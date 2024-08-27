package com.example.finalapplication

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalapplication.databinding.ActivityGraphBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class GraphActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var line_values: ArrayList<Entry> = ArrayList()

        for (i in 0 until 10) {
            var v = Math.random() * 5
            line_values.add(Entry(i.toFloat(), v.toFloat()))
        }

        val linedataset = LineDataSet(line_values, "LineDataSet")
        linedataset.color = Color.parseColor("#C9F0FF")
        linedataset.lineWidth = 5f
        linedataset.setCircleColor(Color.BLACK)

        val linedata = LineData(linedataset)
        binding.lineChart.data = linedata
    }
}