package com.dwiki.nodemcu.utils

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdManager.DiscoveryListener
import android.net.nsd.NsdManager.ResolveListener
import android.net.nsd.NsdServiceInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.net.InetAddress

class NodemcuConnectivityObserver(context: Context): ConnectivityObserver {

    private val nsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager


    override fun observe(): Flow<ConnectivityObserver.Status>{

        return callbackFlow {
            val discoveryListener = object : DiscoveryListener {
                override fun onStartDiscoveryFailed(serviceType: String?, errorCode: Int) {
                    launch { send(ConnectivityObserver.Status.Disconnected) }
                    Log.d("NodeMcuConnectivity", "onStartDiscoveryFailed: ")
                }

                override fun onStopDiscoveryFailed(serviceType: String?, errorCode: Int) {
                    launch { send(ConnectivityObserver.Status.Disconnected) }
                    Log.d("NodeMcuConnectivity", "onStopDiscoveryFailed: ")
                }

                override fun onDiscoveryStarted(regType: String?) {
                    launch { send(ConnectivityObserver.Status.Disconnected) }
                    Log.d("NodeMcuConnectivity", "onDiscoveryStarted: $regType")
                }

                override fun onDiscoveryStopped(serviceType: String?) {
                    launch { send(ConnectivityObserver.Status.Disconnected) }
                    Log.d("NodeMcuConnectivity", "onDiscoveryStopped: ")
                }


                override fun onServiceFound(service: NsdServiceInfo) {
                    when (service.serviceName) {
                        SERVICENAME -> {
                            Log.d("NodeMcuConnectivity", "onServiceFound: ${service.serviceName}")
                            launch { send(ConnectivityObserver.Status.Connected) }
                        }
                    }
                    Log.d("NodeMcuConnectivity", "onServiceFound")
                }

                override fun onServiceLost(service: NsdServiceInfo?) {
                    Log.d("NodeMcuConnectivity", "onServiceLost: ")
                    launch { send(ConnectivityObserver.Status.Disconnected) }
                }
            }
            Log.d("NodeMcuConnectivity", "service start")
            nsdManager.discoverServices("_http._tcp.", NsdManager.PROTOCOL_DNS_SD, discoveryListener)
            awaitClose {
                Log.d("NodeMcuConnectivity", "service stop")
                nsdManager.stopServiceDiscovery(discoveryListener)
            }
        }.distinctUntilChanged()
    }

    companion object {
        const val SERVICENAME = "nodemcu"
    }

}