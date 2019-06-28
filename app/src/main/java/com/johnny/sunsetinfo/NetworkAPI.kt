package com.johnny.sunsetinfo

import com.google.gson.reflect.TypeToken
import okhttp3.Request
import org.jdeferred2.Promise

object NetworkAPI {
    fun getSunsetDataSync(): Promise<SunsetResponse, Exception, Double> {
        val okhttpRequest = Request.Builder()
            .url("https://api.sunrise-sunset.org/json?lat=22.604098&lng=120.3001811&date=today&formatted=0")
            .method("GET", null)
            .build()

        val type = object : TypeToken<SunsetResponse>() {}.type
        return NetworkManager.instance.requestAsync(okhttpRequest, type)
    }
}