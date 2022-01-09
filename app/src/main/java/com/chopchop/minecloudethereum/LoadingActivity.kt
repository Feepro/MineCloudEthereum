package com.chopchop.minecloudethereum

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import render.animations.Fade
import render.animations.Flip
import render.animations.Render
import java.util.*
import kotlin.concurrent.schedule


class LoadingActivity : AppCompatActivity() {
    private lateinit var logoTop: ImageView
    private lateinit var logoBottom: ImageView
    private lateinit var logoText: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        logoTop = findViewById(R.id.top)
        logoBottom = findViewById(R.id.bottom)
        logoText = findViewById(R.id.logoText)

        startLogoAnimation()

    }

    private fun startLogoAnimation() {
        val render = Render(this)
        render.setAnimation(Flip().InY(logoTop))
        render.setDuration(3000)
        render.start()
        render.setAnimation(Flip().InY(logoBottom))
        render.setDuration(3000)
        render.start()
        render.setAnimation(Fade().InUp(logoText))
        render.setDuration(4000)
        render.start()
        Timer().schedule(5000){
            finish()
        }
    }
}