package com.example.authrsf.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authrsf.R
import com.example.authrsf.model.RetrofitClient
import com.example.authrsf.model.UserRequest
import com.example.authrsf.model.UserResponse
import com.google.gson.Gson
import org.jetbrains.annotations.TestOnly
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    private lateinit var signupUsername: EditText
    private lateinit var signupPassword: EditText
    private lateinit var signupButton: Button

    private lateinit var loginRedirect: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signupUsername = findViewById(R.id.signupEmail)
        signupPassword = findViewById(R.id.signupPassword)
        signupButton = findViewById(R.id.buttonSignup)
        loginRedirect = findViewById(R.id.loginRedirect)

        loginRedirect.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }



        signupButton.setOnClickListener {
            val username = signupUsername.text.toString()
            val password = signupPassword.text.toString()

            val request = UserRequest(username, password)

            RetrofitClient.instance.signupUser(request).enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    val res = response.body()
                    if (res != null && res.success) {
                        Toast.makeText(this@SignupActivity, "Signup Success", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(
                            this@SignupActivity,
                            res?.message ?: "Error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                    Toast.makeText(this@SignupActivity, "error: ${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }

        }