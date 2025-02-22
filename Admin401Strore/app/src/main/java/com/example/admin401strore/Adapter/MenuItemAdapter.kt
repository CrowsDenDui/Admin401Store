package com.example.admin401strore.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.admin401strore.Model.Allmenu
import com.example.admin401strore.databinding.ItemBinding
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<Allmenu>,
    databaseRef: DatabaseReference
) : RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {

    private val itemQuantities = IntArray(menuList.size) { 1 }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuItemAdapter.AddItemViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuItemAdapter.AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuList.size

    inner class AddItemViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                val menuItem: Allmenu = menuList[position]
                val uriString: String? = menuItem.foodName
                val uri: Uri = Uri.parse(uriString)
                txtFoodName.text = menuItem.foodName
                txtFoodPrice.text = menuItem.foodPrice
                Glide.with(context).load(uri).into(imgFood)
                txtQuantity.text = quantity.toString()

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
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.txtQuantity.text = itemQuantities[position].toString()
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.txtQuantity.text = itemQuantities[position].toString()
            }
        }

        private fun delQuantity(position: Int) {
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size)
        }

    }


}