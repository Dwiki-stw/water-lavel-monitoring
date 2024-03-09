package com.dwiki.nodemcu.data

import android.content.Context
import android.util.Log
import com.dwiki.nodemcu.data.local.SharedPreferences
import com.dwiki.nodemcu.data.local.model.InfoCisternModel
import com.dwiki.nodemcu.data.remote.Config
import com.dwiki.nodemcu.data.remote.response.MonitoringResponse
import com.dwiki.nodemcu.data.remote.response.PostResponse
import com.dwiki.nodemcu.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Repository(
    context: Context
) {
    companion object {
        private const val HEIGHT = "height"
        private const val WIDTH = "width"
        private  const val CAPACITY = "capacity"
        private const val LASTFILL = "lastFill"
    }

    private val service = Config.getApiService()
    private val sharedPreferences = SharedPreferences.provideSharedPreferences(context)

    suspend fun getDataMonitoring() : Flow<Resource<MonitoringResponse?>?> =
        flow {
            try {
                val response = service.getDataMonitoring()
                if (response.isSuccessful){
                    emit(Resource.Success(response.body()))
                    Log.d("Repository", "getDataMonitoring: Success")
                } else {
                    Log.d("Repository", "getDataMonitoring: Error")
                    emit(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error"))
                Log.d("Repository", "getDataMonitoring: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)

    suspend fun postHeightCistern(height : Int) : Flow<Resource<PostResponse?>?> =
        flow {
            try {
                val response = service.postHeightCistern(height)
                if (response.isSuccessful){
                    emit(Resource.Success(response.body()))
                    Log.d("Repository", "postHeightCistern: Success")
                } else {
                    Log.d("Repository", "postHeightCistern: Error")
                    emit(Resource.Error(response.message()))
                }
            }catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error"))
                Log.d("Repository", "postHeightCistern: ${e.message}")
            }
        }

    fun setInfoCistern(value: InfoCisternModel) {
        val editor = sharedPreferences.edit()
        editor.putString(HEIGHT, value.height)
        editor.putString(WIDTH, value.width)
        editor.putString(CAPACITY, value.capacity)
        editor.apply()
    }

    fun setLastFill(date: String) {
        val editor = sharedPreferences.edit()
        editor.putString(LASTFILL, date)
        editor.apply()
    }

    fun getInfoCistern(): InfoCisternModel {
        val model = InfoCisternModel()
        model.height = sharedPreferences.getString(HEIGHT, "-")
        model.width = sharedPreferences.getString(WIDTH, "-")
        model.capacity = sharedPreferences.getString(CAPACITY, "-")

        return model
    }
    fun getLastFill(): String? {
        return sharedPreferences.getString(LASTFILL, "-")
    }

}