package com.example.authrsf.internship

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.authrsf.R
import com.example.authrsf.model.RetrofitClient
import com.example.authrsf.model.ApiResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class ApplyFragment : Fragment() {

    private lateinit var titleText: TextView
    private lateinit var locationText: TextView
    private lateinit var durationText: TextView
    private lateinit var textDescription: TextView

    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var skillsInput: EditText
    private lateinit var areaSpinner: Spinner
    private lateinit var uploadButton: Button
    private lateinit var submitButton: Button

    private var selectedFilePath: String? = null
    private lateinit var resumeLauncher: ActivityResultLauncher<Intent>

    companion object {
        fun newInstance(title: String, location: String, duration: String, description: String): ApplyFragment {
            val fragment = ApplyFragment()
            val args = Bundle()
            args.putString("internshipTitle", title)
            args.putString("internshipLocation", location)
            args.putString("internshipDuration", duration)
            args.putString("internshipDescription", description)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resumeLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    selectedFilePath = copyFileToCache(uri)
                    if (selectedFilePath != null) {
                        uploadButton.text = "Resume Selected"
                    } else {
                        Toast.makeText(requireContext(), "Unable to get file path", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.activity_apply_fragment, container, false)

        titleText = view.findViewById(R.id.textTitle)
        locationText = view.findViewById(R.id.textLocation)
        durationText = view.findViewById(R.id.textDuration)
        textDescription = view.findViewById(R.id.textDescription)

        nameInput = view.findViewById(R.id.editName)
        emailInput = view.findViewById(R.id.editEmail)
        phoneInput = view.findViewById(R.id.editPhone)
        skillsInput = view.findViewById(R.id.editSkills)
        areaSpinner = view.findViewById(R.id.areaSpinner)

        uploadButton = view.findViewById(R.id.buttonUploadResume)
        submitButton = view.findViewById(R.id.buttonSubmit)

        val areas = arrayOf("Android", "Web", "ML", "AI", "UI/UX")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, areas)
        areaSpinner.adapter = adapter

        titleText.text = arguments?.getString("internshipTitle")
        locationText.text = arguments?.getString("internshipLocation")
        durationText.text = arguments?.getString("internshipDuration")
        textDescription.text = arguments?.getString("internshipDescription")

        uploadButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            resumeLauncher.launch(intent)
        }

        submitButton.setOnClickListener {
            if (selectedFilePath == null) {
                Toast.makeText(requireContext(), "Please select a resume file", Toast.LENGTH_SHORT).show()
            } else {
                uploadApplication()
            }
        }

        return view
    }

    private fun copyFileToCache(uri: Uri): String? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val file = File(requireContext().cacheDir, "resume_${System.currentTimeMillis()}.pdf")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            outputStream.close()
            inputStream?.close()
            file.absolutePath
        } catch (e: Exception) {
            Log.e("ApplyFragment", "File copy error: ${e.message}")
            null
        }
    }

    private fun uploadApplication() {
        val file = File(selectedFilePath!!)
        val requestFile = file.asRequestBody("application/pdf".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("resume", file.name, requestFile)

        val name = nameInput.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val email = emailInput.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val phone = phoneInput.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val skills = skillsInput.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val internshipTitle = titleText.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val areaOfInterest = areaSpinner.selectedItem.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val description = textDescription.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        RetrofitClient.instance.applyInternship(
            name, email, phone, internshipTitle, skills, areaOfInterest, description, body
        ).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                Log.d("RESPONSE_BODY", response.body().toString())
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null && apiResponse.success) {
                        Toast.makeText(requireContext(), "Application Submitted!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Failed: ${apiResponse?.message ?: "Unknown error"}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "HTTP Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}