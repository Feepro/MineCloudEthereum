package com.chopchop.minecloudethereum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.text.DecimalFormat

class BuyGpuActivity : AppCompatActivity() {

    var count = 1
    private lateinit var targetGpu:GPU

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_gpu)

        targetGpu =
        if(intent.hasExtra("gpu"))
            Json.decodeFromString(intent.getStringExtra("gpu")!!)
        else
            GPU("NONE",2,3,GpuFamily.Intell,100);


        val buyBtn = findViewById<CardView>(R.id.backBuyBtn)

        initInfo(targetGpu,count)
        initPriceButtons()
    }

    private fun initInfo(targetGpu: GPU,count:Int) {
        val cardName  = findViewById<TextView>(R.id.cardName)
        val totalPrice = findViewById<TextView>(R.id.totalPrice)
        val mhsTw   = findViewById<TextView>(R.id.mhsTw)
        val ethEq   = findViewById<TextView>(R.id.ethEq)

        cardName.text = targetGpu.name
        totalPrice.text = (count*targetGpu.price).toString()
        mhsTw.text = (count*targetGpu.mhs).toString()
        ethEq.text = "~ "+DecimalFormat("#0.000000000").format(((count*targetGpu.ethPerSec).toDouble() / 1000000000).toBigDecimal())
    }

    private fun initPriceButtons() {
        val countTw = findViewById<TextView>(R.id.countTw)
        val plusBtn = findViewById<ImageView>(R.id.plusBtn)
        val minBtn  = findViewById<ImageView>(R.id.minBtn)
        val backBtn = findViewById<CardView>(R.id.backBuyBtn)

        backBtn.setOnClickListener{
            finish()
        }
        plusBtn.setOnClickListener {
            count++
            countTw.text = count.toString()
            initInfo(targetGpu,count)
        }
        minBtn.setOnClickListener {
            if(countTw.text.toString().toInt()>1){
                count--
                countTw.text = count.toString()
                initInfo(targetGpu,count)
            }
        }
    }
}