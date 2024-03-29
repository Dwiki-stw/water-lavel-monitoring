package com.dwiki.nodemcu.utils

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status{
        Connected, Disconnected
    }
}