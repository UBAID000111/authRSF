package com.example.authrsf.internship


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.authrsf.R
import com.example.authrsf.internship.InternshipActivity
import com.example.authrsf.model.Internship
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.authrsf.model.RetrofitClient

class InternshipActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InternshipAdapter

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internship)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        fetchInternships()

        swipeRefreshLayout.setOnClickListener {
            fetchInternships()
        }
    }

        private fun fetchInternships() {
            swipeRefreshLayout.isRefreshing = true
            RetrofitClient.instance.getInternships().enqueue(object : Callback<List<Internship>> {
                override fun onResponse(call: Call<List<Internship>>, response: Response<List<Internship>>) {
                    swipeRefreshLayout.isRefreshing = false
                    if (response.isSuccessful) {
                        val internships = response.body() ?: emptyList()
                        adapter = InternshipAdapter(internships)
                        recyclerView.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<List<Internship>>, t: Throwable) {
                    swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(this@InternshipActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            })
        }
        }

