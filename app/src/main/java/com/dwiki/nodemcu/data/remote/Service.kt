package com.dwiki.nodemcu.data.remote

import com.dwiki.nodemcu.data.remote.response.MonitoringResponse
import com.dwiki.nodemcu.data.remote.response.PostResponse
import com.dwiki.nodemcu.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

    @GET("getData")
    suspend fun getDataMonitoring() : Response<MonitoringResponse>

    @GET("getDuration")
    suspend fun getDuration(): Response<String>

    @POST("postData")
    suspend fun postHeightCistern(
        @Body height: Int
    ): Response<PostResponse>
}