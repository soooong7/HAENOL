package com.example.finalapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.finalapplication.databinding.ItemBinfoBinding

class PhpViewHolder(val binding: ItemBinfoBinding) : RecyclerView.ViewHolder(binding.root)

class PhpAdapter(val context: PhpActivity, val itemList: ArrayList<BinfoData>): RecyclerView.Adapter<PhpViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhpViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PhpViewHolder(ItemBinfoBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: PhpViewHolder, position: Int) {
        val data = itemList.get(position)

        holder.binding.run {
            tvName.text = data.name
            tvRank.text = data.rank.toString()
            tvAddr.text = data.addr

            root.setOnClickListener {
                Toast.makeText(context, "Root", Toast.LENGTH_LONG).show()
                Intent(context, BeachActivity::class.java).apply {
                    putExtra("name", tvName.text)
                    putExtra("rank", tvRank.text)
                    putExtra("addr", tvAddr.text)
                }.run {
                    context.startActivity(this)
                }
            }

            tvName.setOnClickListener {
                Toast.makeText(context, "Name", Toast.LENGTH_LONG).show()
            }

            imageView.setOnClickListener {
                Toast.makeText(context, "Image", Toast.LENGTH_LONG).show()
            }
        }
    }
}
