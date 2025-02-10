package com.example.admin401strore.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.admin401strore.databinding.ItemBinding

class AddItemAdapter(private val menuItemName: ArrayList<String>, private val menuItemPrice: ArrayList<String>, private val menuItemImage: ArrayList<Int>) : RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>() {

    private val itemQuantities = IntArray(menuItemName.size){1}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddItemAdapter.AddItemViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddItemAdapter.AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItemName.size

    inner class AddItemViewHolder(private val binding: ItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                txtFoodName.text = menuItemName[position]
                txtFoodPrice.text = menuItemPrice[position]
                txtQuantity.text = quantity.toString()
                imgFood.setImageResource(menuItemImage[position])

                btnMinus.setOnClickListener {
                    decreaseQuantity(position)
                }

                btnPlus.setOnClickListener {
                    increaseQuantity(position)
                }

                btnTrash.setOnClickListener {
                    delQuantity(position)
                }
            }
        }
        private fun decreaseQuantity(position: Int) {
            if(itemQuantities[position]>1){
                itemQuantities[position]--
                binding.txtQuantity.text = itemQuantities[position].toString()
            }
        }

        private fun increaseQuantity(position: Int) {
            if(itemQuantities[position]<10){
                itemQuantities[position]++
                binding.txtQuantity.text = itemQuantities[position].toString()
            }
        }

        private fun delQuantity(position: Int) {
            menuItemName.removeAt(position)
            menuItemPrice.removeAt(position)
            menuItemImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuItemName.size)
        }

    }



}