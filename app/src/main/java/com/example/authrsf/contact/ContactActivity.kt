package com.example.authrsf.contact

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.authrsf.R
import org.json.JSONObject
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request

class ContactActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextQuery: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        editTextName = findViewById(R.id.NameContact)
        editTextQuery = findViewById(R.id.queryTab)
        editTextDescription = findViewById(R.id.descriptionTab)
        buttonSubmit = findViewById(R.id.submit_button)

        buttonSubmit.setOnClickListener {
            submitFeedback()
        }
    }

    private fun submitFeedback() {
        val name = editTextName.text.toString().trim()
        val query = editTextQuery.text.toString().trim()
        val description = editTextDescription.text.toString().trim()

        if (name.isEmpty() || query.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val url = "http://10.0.2.2:8000/api/user/feedback"
        val jsonBody = JSONObject()
        jsonBody.put("name", name)
        jsonBody.put("query", query)
        jsonBody.put("description", description)

        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonBody,
            { response ->
                val message = response.optString("message")
                val success = response.optBoolean("success")
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                if (success) {
                    editTextName.setText("")
                    editTextQuery.setText("")
                    editTextDescription.setText("")
                }
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        Volley.newRequestQueue(this).add(request)
    }
}

