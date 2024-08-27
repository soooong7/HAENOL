package com.example.finalapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding

    lateinit var toggle: ActionBarDrawerToggle

    lateinit var headerView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DrawerLayout 토글
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            R.string.drawer_opened,
            R.string.drawer_closed
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // DrawerLayout 메뉴
        binding.mainDrawerView.setNavigationItemSelectedListener(this)

        headerView = binding.mainDrawerView.getHeaderView(0)
        val button = headerView.findViewById<Button>(R.id.btnAuth)
        button.setOnClickListener {
            Log.d("mobileApp", "button.setOnClickListener")

            val intent = Intent(this, AuthActivity::class.java)
            if(button.text.equals("로그인")) {
                intent.putExtra("status", "logout")
            }
            else if(button.text.equals("로그아웃")) {
                intent.putExtra("status", "login")
            }
            startActivity(intent)
            binding.drawer.closeDrawers()
        }

        binding.btnSearch.setOnClickListener{
            if(MyApplication.checkAuth()) {
                val name = binding.edtName.text.toString()

                val call: Call<XmlResponse> = RetrofitConnection.xmlNetworkService.getXmlList(
                    name,
                    1,
                    10,
                    "xml",
                    "eJ6IXdAifaJo0ZgGSoy2U2PDQ+UjdItwIX1pQOGv3agnLPZu+eGuhlwI+nZyw5uxxES+i8/Nptfaf9QlPn9PzQ==" // 일반인증키(Decoding)
                )

                call?.enqueue(object : Callback<XmlResponse> {
                    override fun onResponse(
                        call: Call<XmlResponse>,
                        response: Response<XmlResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("mobileApp", "$response")
                            Log.d("mobileApp", "${response.body()}")
                            binding.xmlRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                            binding.xmlRecyclerView.adapter = XmlAdapter(response.body()!!.body!!.items!!.item)
                            binding.xmlRecyclerView.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
                        }
                    }

                    override fun onFailure(call: Call<XmlResponse>, t: Throwable) {
                        Log.d("mobileApp", "onFailure ${call.request()}")
                    }
                })
            }
            else {
                Toast.makeText(this,"인증을 먼저 진행해주세요..",Toast.LENGTH_LONG).show()
            }
        }

        // 알림
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions() ) {
            if (it.all { permission -> permission.value == true }) {
                noti()
            }
            else {
                Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.notificationButton.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this,"android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED) {
                    noti()
                }
                else {
                    permissionLauncher.launch( arrayOf( "android.permission.POST_NOTIFICATIONS"  ) )
                }
            }
            else {
                noti()
            }
        }

        binding.mapButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.7749,-122.4194"))
            startActivity(intent)
        }

    }

    // Drawer 토글
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Drawer 메뉴
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_notice -> {
                Log.d("mobileapp", "공지사항 DB 메뉴")
                val intent = Intent(this, NoticeActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                return true
            }

            R.id.item_php -> {
                Log.d("mobileapp", "해수욕장 순위 Php 메뉴")
                val intent = Intent(this, PhpActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }

            R.id.item_board -> {
                Log.d("mobileapp", "게시판 firebase 메뉴")
                val intent = Intent(this, BoardActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                return true
            }

            R.id.item_graph -> {
                Log.d("mobileapp", "방문자 수 그래프 메뉴")
                val intent = Intent(this, GraphActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                return true
            }

            R.id.item_youtube -> {
                Log.d("mobileapp", "바다 ASMR 메뉴")
                val intent = Intent(this, YoutubeActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                return true
            }

            R.id.item_food -> {
                Log.d("mobileapp", "부산 맛집 메뉴")
                val intent = Intent(this, JsonActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                return true
            }


        }
        return false
    }

    // 로그인 후 돌아왔을 때
    override fun onStart() {
        super.onStart()

        val button = headerView.findViewById<Button>(R.id.btnAuth)
        val tv = headerView.findViewById<TextView>(R.id.tvID)

        if(MyApplication.checkAuth() || MyApplication.email != null) {
            button.text = "로그아웃"
            tv.text = "${MyApplication.email}님 \n 반가워요!"
        }
        else {
            button.text ="로그인"
            tv.text = "안녕하세요!"
        }
    }

    // 알림
    fun noti(){
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){     // 26 버전 이상
            val channelId="one-channel"
            val channelName="My Channel One"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {   // 채널에 다양한 정보 설정
                description = "My Channel One Description"
                setShowBadge(true)  // 앱 런처 아이콘 상단에 숫자 배지를 표시할지 여부를 지정
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(true)
            }
            // 채널을 NotificationManager에 등록
            manager.createNotificationChannel(channel)
            // 채널을 이용하여 builder 생성
            builder = NotificationCompat.Builder(this, channelId)
        }
        else {  // 26 버전 이하
            builder = NotificationCompat.Builder(this)
        }

        // 알림의 기본 정보
        builder.run {
            setSmallIcon(R.drawable.small)
            setWhen(System.currentTimeMillis())
            setContentTitle("해수욕장")
            setContentText("해놀을 이용해주셔서 감사합니다!")
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.big))
        }

        manager.notify(11, builder.build())
    }

}