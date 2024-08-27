package com.example.finalapplication

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalapplication.MyAdapter
import com.example.finalapplication.databinding.ActivityNoticeBinding
import java.io.BufferedReader
import java.io.File
import java.text.SimpleDateFormat

class NoticeActivity : AppCompatActivity() {
    lateinit var binding : ActivityNoticeBinding
    var datas: MutableList<String>? = null
    lateinit var adapter: MyAdapter
    lateinit var sharedPreference : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)

        val idStr = sharedPreference.getString("id", "")
        binding.noticeTitle.text = idStr

        val manageStr = sharedPreference.getString("manage", "")
        binding.manageId.text = manageStr

        val color = sharedPreference.getString("color", "#00ff00")
        binding.lastsaved.setBackgroundColor(Color.parseColor(color))

        val size = sharedPreference.getString("size", "16.0f")
        binding.lastsaved.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())


        datas = mutableListOf<String>()
        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from todo_tb", null)
        while(cursor.moveToNext()) {
            datas?.add(cursor.getString(1))
        }
        db.close()

        // 파일 읽기 (파일 새로 생성할 땐 이 부분 주석 처리 후 생성 -> 주석 해제)
        val file = File(filesDir, "test.txt")
        val readstream: BufferedReader = file.reader().buffered()
        binding.lastsaved.text = "최근 작성 시간 : " + readstream.readLine()

        adapter=MyAdapter(datas)
        binding.recyclerView.adapter=adapter
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager=layoutManager
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            it.data!!.getStringExtra("result")?.let {// "result"에 값이 저장되어 있으면(non-null)
                if(it != "") {
                    datas?.add(it)
                    adapter.notifyDataSetChanged()

                    val file = File(filesDir, "test.txt")
                    val readstream: BufferedReader = file.reader().buffered()
                    binding.lastsaved.text = "최근 작성 시간 : " + readstream.readLine()
                }
            }
        }

        binding.mainFab.setOnClickListener {
            val intent = Intent(this, AddDbActivity::class.java)

            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            intent.putExtra("today",dateFormat.format(System.currentTimeMillis()))

            requestLauncher.launch(intent)
        }
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

    override fun onResume() {
        super.onResume()

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val color = sharedPreference.getString("color", "#00ff00")
        binding.lastsaved.setBackgroundColor(Color.parseColor(color))

        val idStr = sharedPreference.getString("id", "")
        binding.noticeTitle.text = idStr

        val size = sharedPreference.getString("size", "16.0f")
        binding.lastsaved.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())

        val manageStr = sharedPreference.getString("manage", "")
        binding.manageId.text = manageStr
    }

}