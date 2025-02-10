package com.example.admin401strore.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.admin401strore.databinding.PendingOrderItemBinding

class PendingOrderAdapter(
    private val customerName: ArrayList<String>,
    private val Quantity: ArrayList<String>,
    private val imgFood: ArrayList<Int>,
    private val context: Context
) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding =
            PendingOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerName.size

    inner class PendingOrderViewHolder(private val binding: PendingOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
            binding.apply {
                txtPendingCustomerName.text = customerName[position]
                txtPendingQuantity.text = Quantity[position]
                imgPendingFood.setImageResource(imgFood[position])

                btnAccept.apply {
                    if (!isAccepted) {
                        text = "Accept"
                    } else {
                        text = "Dispatch"
                    }
                    setOnClickListener {
                        if (!isAccepted) {
                            text = "Dispatch"
                            isAccepted = true
                            showToast("Order is accepted")
                        } else {
                            customerName.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order is Dispatched")
                        }
                    }
                }
            }
        }
        fun showToast(message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}