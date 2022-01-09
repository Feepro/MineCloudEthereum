package com.chopchop.minecloudethereum

import android.content.ContentValues.TAG
import android.util.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

@Serializable
data class ExchangeEntity(
    var ethToRub : String?,
    var rubToEth : String?
)

@Serializable
data class ExchangeHistory(
    var history: ArrayList<Double>?
)

interface ExchangeListener{
    fun getValidExchange():ExchangeEntity?
    fun getValidExchangeHistory():ExchangeHistory?
}

class Exchange : ExchangeListener {
    override fun getValidExchange(): ExchangeEntity? {
        val url = URL("https://www.calc.ru/Ethereum-k-rublyu-online.html")
        val urlConnection = url.openConnection() as HttpURLConnection
        return try {
            val text = urlConnection.inputStream.bufferedReader().readText()
            val ethToRub = text.substringAfter("<b>1 ETH</b> = <b>").substringBefore(" RUB")
            val rubToEth = text.substringAfter("<b>1 RUB</b> = <b>").substringBefore(" ETH")
            val currentExchange = ExchangeEntity(ethToRub, rubToEth)

            Log.i(TAG, "getValidExchange: "+Json.encodeToString(currentExchange))
            currentExchange
        }catch (e: Exception){
            e.printStackTrace()

            null
        }finally {
            urlConnection.disconnect()
        }
    }

    override fun getValidExchangeHistory(): ExchangeHistory? {
        return null
    }

}

