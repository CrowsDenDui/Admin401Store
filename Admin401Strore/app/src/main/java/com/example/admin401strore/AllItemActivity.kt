package com.example.admin401strore

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin401strore.Adapter.AddItemAdapter
import com.example.admin401strore.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val menuFoodName = listOf("Bột Chiên", "Nui xào", "Mì xào", "Miến xào")
        val menuFoodPrice = listOf("30.000VND", "35.000VND", "35.000VND", "35.000VND")
        val menuFoodImage = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4
        )

        binding.btnBack.setOnClickListener {
            finish()
        }

        val adapter = AddItemAdapter(ArrayList(menuFoodName), ArrayList(menuFoodPrice), ArrayList(menuFoodImage))

        binding.rvMenu.layoutManager = LinearLayoutManager(this)
        binding.rvMenu.adapter = adapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}