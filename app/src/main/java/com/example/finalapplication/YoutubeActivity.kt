package com.example.finalapplication

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalapplication.databinding.ActivityBoardBinding
import com.example.finalapplication.databinding.ActivityYoutubeBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import java.util.Random

class YoutubeActivity : AppCompatActivity() {
    lateinit var binding : ActivityYoutubeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val random = Random()
        val num = random.nextInt(3)

        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId : String
                if (num == 0) videoId = "50P-2N6uex4"
                else if (num == 1) videoId = "6rT9LrvgTDw"
                else videoId = "n4Mdh3TEq_k"

                youTubePlayer.cueVideo(videoId, 0f)
            }
        })

        // 애니메이션
        binding.btnTranslate.setOnClickListener{
            var anim = AnimationUtils.loadAnimation(this, R.anim.translate)
            binding.btnTranslate.startAnimation(anim)
        }

        binding.btnScale.setOnClickListener{
            var anim = AnimationUtils.loadAnimation(this, R.anim.scale)
            binding.btnScale.startAnimation(anim)
        }

        binding.btnRotate.setOnClickListener{
            var anim = AnimationUtils.loadAnimation(this, R.anim.rotate)
            binding.btnRotate.startAnimation(anim)
        }

        binding.btnAlpha.setOnClickListener{
            var anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
            binding.btnAlpha.startAnimation(anim)
        }

        binding.btnWave.setOnClickListener{
            var anim = AnimationUtils.loadAnimation(this, R.anim.wave)
            binding.btnWave.startAnimation(anim)
        }

        var beachAnimation : AnimationDrawable
        val beachImage = binding.beachImage.apply {
            setBackgroundResource(R.drawable.ani)
            beachAnimation = background as AnimationDrawable
        }
        beachAnimation.start()

    }
}