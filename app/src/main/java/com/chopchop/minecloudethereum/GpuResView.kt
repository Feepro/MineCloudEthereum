package com.chopchop.minecloudethereum

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CustomRecyclerAdapter(private val gpu: List<GPU>,private val context: Context) : RecyclerView
.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val largeTextView: TextView = itemView.findViewById(R.id.gpu_name)
        val smallTextView: TextView = itemView.findViewById(R.id.gpu_speed)
        val parentBackground : ConstraintLayout = itemView.findViewById(R.id.parentBackground)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.gpu_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.largeTextView.text = gpu[position].name
        holder.smallTextView.text = gpu[position].name
        holder.parentBackground.setBackgroundResource(
            when(gpu[position].ethPerSec){
                in 0..15 ->   R.color.level1
                in 15..20 ->  R.color.level2
                in 20..25 ->  R.color.level3
                in 25..40 ->  R.color.level4
                else ->       R.color.level5
            }
        )
        holder.parentBackground.setOnClickListener {
            val buyIntent = Intent(context,BuyGpuActivity::class.java)
            buyIntent.putExtra("gpu", Json.encodeToString(gpu[position]))
            context.startActivity(buyIntent)
        }

    }

    override fun getItemCount() = gpu.size
}