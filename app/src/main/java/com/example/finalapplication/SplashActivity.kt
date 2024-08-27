package com.example.finalapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalapplication.databinding.ActivityMainBinding
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val backgroundExecutor: ScheduledExecutorService
                = Executors.newSingleThreadScheduledExecutor()

        val mainExecutor: Executor = ContextCompat.getMainExecutor(this)
        backgroundExecutor.schedule({
            mainExecutor.execute{
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2, TimeUnit.SECONDS) // 2초 후
    }
}