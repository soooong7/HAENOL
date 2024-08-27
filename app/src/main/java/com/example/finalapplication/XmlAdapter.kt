package com.example.finalapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalapplication.databinding.ItemMainBinding

class XmlViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)

class XmlAdapter(val datas:MutableList<myXmlItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return XmlViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as XmlViewHolder).binding
        val model = datas!![position]

        binding.staName.text = model.staNm
        binding.sidoName.text = model.sidoNm
        binding.gugunName.text = model.gugunNm
        binding.Knd.text = model.beachKnd
        binding.Link.text = model.linkAddr
        binding.Tel.text = model.linkTel

        Glide.with(binding.root)
            .load(model.beachImg)
            .override(400,300)
            .into(binding.urlImage)
    }
}