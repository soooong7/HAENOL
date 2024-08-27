package com.example.joyceapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalapplication.databinding.ItemMain2Binding

class JsonViewHolder(val binding: ItemMain2Binding): RecyclerView.ViewHolder(binding.root)

class Xml1Adapter(val datas:MutableList<myJsonItems>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JsonViewHolder(ItemMain2Binding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as JsonViewHolder).binding
        val model = datas!![position]

        binding.rstrNm.text = model.RSTR_NM
        binding.areaNm.text = model.AREA_NM

        Glide.with(binding.root)
            .load(model.FOOD_IMG_URL)
            .override(400,400)
            .into(binding.foodImage)
    }
}