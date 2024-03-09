package com.dwiki.nodemcu.data.remote.response

import com.google.gson.annotations.SerializedName

data class PostResponse(

    @field:SerializedName("isSuccess")
    val waterCapacity: Boolean = false,

    @field:SerializedName("message")
    val message: String = ""
)
