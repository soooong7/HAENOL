package com.example.finalapplication

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.finalapplication.databinding.ActivityAddDbBinding
import java.io.File
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat

class AddDbActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddDbBinding
    lateinit var sharedPreference : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityAddDbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val color = sharedPreference.getString("color", "#00ff00")
        binding.date.setTextColor(Color.parseColor(color))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var date = intent.getStringExtra("today")
        binding.date.text = date

        binding.btnSave.setOnClickListener {
            val edt_str = binding.addEditView.text.toString()
            val intent = intent
            intent.putExtra("result", edt_str)
            setResult(Activity.RESULT_OK, intent)

            // db에 저장하기
            val db = DBHelper(this).writableDatabase
            db.execSQL("insert into todo_tb (todo) values (?)", arrayOf<String>(edt_str))
            db.close()

            // 파일 저장하기
            val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val file = File(filesDir, "test.txt")
            val writestream: OutputStreamWriter = file.writer()
            writestream.write(dateFormat.format(System.currentTimeMillis()))
            writestream.flush()

            finish()
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = intent
        setResult(Activity.RESULT_OK, intent)
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === R.id.menu_main_setting) {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}