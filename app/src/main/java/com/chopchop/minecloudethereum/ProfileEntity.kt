package com.chopchop.minecloudethereum

import kotlinx.serialization.Serializable

@Serializable
data class ProfileEntity(
    val id:Int = 0,
    var balance:Long = 0,//-9e 1234567899 = 1,234567809 ETH
    var gpuArray: ArrayList<GPU>?,
    var referCount: Int = 0,
    var totalEthPerSec:Long = 15
)
