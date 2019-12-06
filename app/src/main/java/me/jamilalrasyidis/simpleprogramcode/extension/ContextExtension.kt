package me.jamilalrasyidis.simpleprogramcode.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import me.jamilalrasyidis.simpleprogramcode.ui.home.HomeActivity

fun Context.getSharedPreferencesName() : String {
    return this.packageName + ".sharedPref"
}

@Suppress("DEPRECATION")
fun Context.isConnectedToWifi(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
        val network = cm.activeNetwork
        val capability = cm.getNetworkCapabilities(network)
        capability != null && when {
            capability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> false
            else -> false
        }
    } else when (cm.activeNetworkInfo?.type) {
        ConnectivityManager.TYPE_WIFI -> true
        ConnectivityManager.TYPE_MOBILE -> true
        else -> false
    }
}