package com.example.finalapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalapplication.databinding.ActivityJsonBinding
import com.example.joyceapplication.JsonResponse
import com.google.gson.annotations.JsonAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JsonActivity : AppCompatActivity() {
    lateinit var binding: ActivityJsonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJsonBinding.inflate(layoutInflater)
        setContentView(binding.root)

            val call: Call<JsonResponse> = RetrofitConnection.JsonNetworkService.getJsonList(
                1,
                "8GUbaz5bmDaJ0qh6YcMDN4GPxzg9h4rrzQXHnBNTyDZ4A68Zyldn7JbkXe8T4HL6"
            )

            call?.enqueue(object : Callback<JsonResponse> {
                override fun onResponse(
                    call: Call<JsonResponse>,
                    response: Response<JsonResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("mobileApp", "$response")
                        Log.d("mobileApp", "${response.body()}")
//                        binding.jsonRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
//                        binding.jsonRecyclerView.adapter = JsonAdapter(response.body()!!.body!!.items!!.item)
//                        binding.jsonRecyclerView.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
                    }
                }

                override fun onFailure(call: Call<JsonResponse>, t: Throwable) {
                    Log.d("mobileApp", "onFailure ${call.request()}")
                }
            }
        )
    }
}
