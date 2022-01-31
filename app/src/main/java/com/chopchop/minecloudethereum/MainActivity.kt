package com.chopchop.minecloudethereum

import android.R.attr.button
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.hanks.htextview.evaporate.EvaporateTextView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import render.animations.Attention
import render.animations.Render
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {

    var onResumed = true;
    val debugMode = true;
    val updateBalanceDelay = 1;
    val debugProfile  = ProfileEntity(
        123456,
        0,
        arrayListOf(
            GPU("RX123"),
            GPU("RX321")
        ),
        0,
        1500000
    )
    val debugExchangeEntity = ExchangeEntity(
        "283 094",
        "0.0000035"
    )
    lateinit var currentUser:ProfileEntity;
    lateinit var currentExchange:ExchangeEntity;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //startActivity(Intent(this,LoadingActivity::class.java))

        if (debugMode){
            currentUser = debugProfile;
            currentExchange = debugExchangeEntity
        }
        exchangeRatesTimerStart()
        balanceTimerStart()
        findViewById<ImageView>(R.id.refreshExchangeBtn).setOnClickListener { GlobalScope.launch { updateExchangeUi()} }  //button refresh Exchange

        findViewById<CardView>(R.id.upSpeedCard).setOnClickListener {
            showGpuActivity(currentUser)
        }

        findViewById<ImageView>(R.id.showGPU).setOnClickListener {
            showGpuActivity(currentUser)
        }
        //Log.i("TAG", "onCreate: "+ Json.encodeToString(ProfileEntity(1,2,ArrayList<GPU?>(),3)))

    }

    private fun showGpuActivity(currentUser: ProfileEntity) {
        val gpuIntent = Intent(this,GpuActivity::class.java)
        gpuIntent.putExtra("currentUser", Json.encodeToString(currentUser))
        startActivity(gpuIntent)
    }

    private fun balanceTimerStart() {
            GlobalScope.launch {
                while (true) {
                    if(onResumed) {
                        currentUser.balance += currentUser.totalEthPerSec * updateBalanceDelay
                        runOnUiThread {
                            updateBalance(currentExchange.rubToEth!!)
                            Log.i(TAG,
                                "balanceTimerStart: " + ((currentUser.balance.toDouble() / 1000000000).toBigDecimal()
                                    .toPlainString())
                            )
                        }
                        delay(1000 * updateBalanceDelay.toLong())
                    }
                }
            }
    }

    private fun exchangeRatesTimerStart() {
        val timeToUpdateEx = findViewById<TextView>(R.id.timeToUpdateEx)
        val timeToUpdateEx2 = findViewById<TextView>(R.id.timeToUpdateEx2)

        GlobalScope.launch{
            while (true) {
                updateExchangeUi()
                delay(60000)
            }
        } //update Exchange
        GlobalScope.launch{
            var timer = 60;
            while (true) {
                runOnUiThread {
                    timeToUpdateEx.text =
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"))
                    timeToUpdateEx2.text = "${timer}s"

                }
                delay(1000)
                if (timer != 0)
                    timer--
                else
                    timer = 60
            }
        }    //update Timer
    }

    private suspend fun updateExchangeUi(){
        if(onResumed) {
            val ethToRubTW = findViewById<TextView>(R.id.ethToRub)
            val rubToEth = findViewById<TextView>(R.id.rubToEth)
            val exchangeCard = findViewById<CardView>(R.id.exchangeCard)

            currentExchange = Exchange().getValidExchange()!! //todo
            runOnUiThread {
                if (currentExchange != null) {
                    ethToRubTW.text = currentExchange.ethToRub
                    rubToEth.text = currentExchange.rubToEth
                    updateBalance(currentExchange.rubToEth!!) //todo

                    findViewById<ImageView>(R.id.refreshExchangeBtn).startAnimation(
                        AnimationUtils.loadAnimation(
                            baseContext,
                            R.anim.rotate360
                        )
                    )

                    val render = Render(baseContext)
                    render.setAnimation(Attention().Bounce(exchangeCard))
                    render.start()


                } else {
                    //ConnectionError TODO
                }
            }
        }
    }
    private fun updateBalance(rubToEth:String){
        if(onResumed) {
            val balanceTW = findViewById<EvaporateTextView>(R.id.balance)
            val rubBalanceTW = findViewById<EvaporateTextView>(R.id.rubBalance)


            balanceTW.animateText(
                DecimalFormat("#0.000000000").format(
                    (currentUser.balance.toDouble() / 1000000000).toBigDecimal()
                )
            )

            rubBalanceTW.animateText(
                "~ " + DecimalFormat("0.000").format(
                    ((
                            currentUser.balance.toDouble() / 1000000000 * currentExchange.ethToRub!!
                                .replace(" ", "")
                                .toInt()))
                        .toBigDecimal()
                )
            )
        }
    }
    override fun onPause() {
        super.onPause()
        onResumed = false
    }

    override fun onResume() {
        super.onResume()
        onResumed = true;
    }
}
