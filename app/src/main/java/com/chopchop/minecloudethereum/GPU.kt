package com.chopchop.minecloudethereum

import kotlinx.serialization.Serializable

@Serializable
data class GPU (
    val name:String = "RX123",
    val ethPerSec:Int = 0,
    val mhs:Int = 0
)
