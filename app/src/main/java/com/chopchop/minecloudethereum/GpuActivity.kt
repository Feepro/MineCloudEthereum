package com.chopchop.minecloudethereum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hanks.htextview.rainbow.RainbowTextView
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class GpuActivity : AppCompatActivity() {

    var topGPU = arrayListOf<GPU>(
        GPU("1",1,1,GpuFamily.Intell),
        GPU("2",1,1,GpuFamily.Intell),
        GPU("3",2,2,GpuFamily.none)
    )

    var intellGPU = arrayListOf<GPU>()
    var emgGPU = arrayListOf<GPU>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpu)

        topGPU = Json.decodeFromString(resources.getString(R.string.allGPU))
        topGPU.sortByDescending { it.ethPerSec }
        topGPU.forEach { gpu ->
            when(gpu.family){
                GpuFamily.Intell -> intellGPU.add(gpu)
                GpuFamily.Emg ->emgGPU.add(gpu)
                else -> Toast.makeText(this,"Обнаружена неизвестная видеокарта",Toast.LENGTH_SHORT)
            }
        }


        val currentUser: ProfileEntity;

        if (intent.hasExtra("currentUser")){
            currentUser = Json.decodeFromString(intent.getStringExtra("currentUser")!!)

            val userGpuList = findViewById<RecyclerView>(R.id.user_gpu_list)
            userGpuList.layoutManager = LinearLayoutManager(this)
            userGpuList.adapter = CustomRecyclerAdapter(currentUser.gpuArray!!,this)
        } else
            Toast.makeText(this,"Пользователь не определен",Toast.LENGTH_SHORT)

        val top_gpu_list = findViewById<RecyclerView>(R.id.top_gpu_list)
        top_gpu_list.layoutManager = LinearLayoutManager(this)
        top_gpu_list.adapter = CustomRecyclerAdapter(topGPU,this)

        findViewById<ImageView>(R.id.upgradeIntell).setOnClickListener {
            top_gpu_list.adapter = CustomRecyclerAdapter(intellGPU,this)
        }
        findViewById<ImageView>(R.id.upgradeEmd).setOnClickListener {
            top_gpu_list.adapter = CustomRecyclerAdapter(emgGPU,this)
        }
        findViewById<RainbowTextView>(R.id.upgradeAll).setOnClickListener {
            top_gpu_list.adapter = CustomRecyclerAdapter(topGPU,this)
        }
    }
}