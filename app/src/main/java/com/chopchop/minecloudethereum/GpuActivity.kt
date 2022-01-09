package com.chopchop.minecloudethereum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GpuActivity : AppCompatActivity() {

    var debugGpuList = arrayListOf<GPU>(
        GPU("1",1,1),
        GPU("2",1,1),
        GPU("3",2,2)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpu)

        debugGpuList =  Json.decodeFromString(resources.getString(R.string.allGPU))



        val userGpuList = findViewById<RecyclerView>(R.id.user_gpu_list)
        userGpuList.layoutManager = LinearLayoutManager(this)
        userGpuList.adapter = CustomRecyclerAdapter(debugGpuList)
        val top_gpu_list = findViewById<RecyclerView>(R.id.top_gpu_list)
        top_gpu_list.layoutManager = LinearLayoutManager(this)
        top_gpu_list.adapter = CustomRecyclerAdapter(debugGpuList)


    }
}