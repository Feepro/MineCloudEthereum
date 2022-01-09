package com.chopchop.gpugenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class generator : AppCompatActivity() {
    val gpuList = ArrayList<GPU>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generator)

        findViewById<Button>(R.id.add).setOnClickListener {

        }
    }
}