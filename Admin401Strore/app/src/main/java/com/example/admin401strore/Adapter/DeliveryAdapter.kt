package com.example.admin401strore.Adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.admin401strore.databinding.DeliveryItemBinding

class DeliveryAdapter(private val customerName: ArrayList<String>, private val moneyStatus: ArrayList<String>) : RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerName.size

    inner class DeliveryViewHolder(private val binding: DeliveryItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                txtCustomerName.text = customerName[position]
                txtStatus.text = moneyStatus[position]
                val colorMap = mapOf(
                    "Received" to Color.GREEN, "Not Received" to Color.RED, "Pending" to Color.GRAY
                )

                txtStatus.setTextColor(colorMap[moneyStatus[position]]?:Color.BLACK)

                imgStatus.backgroundTintList = ColorStateList.valueOf(colorMap[moneyStatus[position]]?:Color.BLACK)
            }
        }

    }
}