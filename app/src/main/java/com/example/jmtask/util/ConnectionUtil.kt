package com.example.jmtask.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart

class ConnectivityStateManager(context: Context) {

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun observeNetworkState(): Flow<Boolean> {
        val isConnectedFlow = MutableStateFlow(isConnected())

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                isConnectedFlow.value = isConnected()
            }

            override fun onLost(network: Network) {
                isConnectedFlow.value = false
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)

        return isConnectedFlow
            .onStart { emit(isConnected()) }
//            .asStateFlow()
    }

    private fun isConnected(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}
