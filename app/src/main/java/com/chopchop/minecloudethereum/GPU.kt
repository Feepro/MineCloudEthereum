package com.chopchop.minecloudethereum

import kotlinx.serialization.Serializable

enum class GpuFamily {
    Intell,Emg,none
}

@Serializable
data class GPU (
    val name:String = "RX123",
    val ethPerSec:Int = 0,
    val mhs:Int = 0,
    val family:GpuFamily = GpuFamily.none
)
