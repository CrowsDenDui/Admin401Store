package com.example.admin401strore

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin401strore.Adapter.DeliveryAdapter
import com.example.admin401strore.Adapter.PendingOrderAdapter
import com.example.admin401strore.databinding.ActivityPendingOrderBinding

class PendingOrderActivity : AppCompatActivity() {
    private val binding : ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val orderCustomerName = arrayListOf("Wang", "Tài", "Quân Huỳnh",)
        val orderQuantity = arrayListOf("8", "5", "2")
        val orderFoodImage = arrayListOf(R.drawable.menu1, R.drawable.menu2, R.drawable.menu3)
        val adapter = PendingOrderAdapter(orderCustomerName, orderQuantity, orderFoodImage, this)
        binding.rvPendingOrder.adapter = adapter
        binding.rvPendingOrder.layoutManager = LinearLayoutManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}