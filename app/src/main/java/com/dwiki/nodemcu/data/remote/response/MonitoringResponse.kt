package com.dwiki.nodemcu.data.remote.response

import com.google.gson.annotations.SerializedName

data class MonitoringResponse(

    @field:SerializedName("water_capacity")
    val waterCapacity: Int = 0,

    @field:SerializedName("duration")
    val duration: String = "-",

    @field:SerializedName("state_pump")
    val statusPump: Boolean = false
)
