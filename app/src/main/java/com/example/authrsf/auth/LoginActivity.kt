package com.example.authrsf.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authrsf.R
import com.example.authrsf.contact.ContactActivity
import com.example.authrsf.internship.InternshipActivity
import com.example.authrsf.model.RetrofitClient
import com.example.authrsf.model.UserRequest
import com.example.authrsf.model.UserResponse
import com.google.gson.Gson
import org.jetbrains.annotations.TestOnly
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class LoginActivity : AppCompatActivity() {

    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var signupRedirect: TextView

    private lateinit var backgroundCircle: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEmail = findViewById(R.id.LoginEmail)
        loginPassword = findViewById(R.id.LoginPassword)
        loginButton = findViewById(R.id.buttonLogin)
        signupRedirect = findViewById(R.id.signupRedirect)
        backgroundCircle = findViewById(R.id.backgroundCircle)

        signupRedirect.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        loginButton.setOnClickListener {
            val username = loginEmail.text.toString()
            val password = loginPassword.text.toString()

            val request = UserRequest(username, password)

            RetrofitClient.instance.loginUser(request).enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    val res = response.body()
                    if (res != null && res.success) {
                        Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT).show()
                        val intent2 = Intent(this@LoginActivity, InternshipActivity::class.java)
                        startActivity(intent2)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, res?.message ?: "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}

