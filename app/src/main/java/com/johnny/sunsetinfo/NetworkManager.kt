package com.johnny.sunsetinfo

import android.os.Handler
import android.os.Looper
import com.google.gson.GsonBuilder
import okhttp3.*
import org.jdeferred2.Promise
import org.jdeferred2.impl.DeferredObject
import java.io.IOException
import java.lang.reflect.Type

class NetworkManager {
    companion object {
        val instance: NetworkManager by lazy {
            NetworkManager()
        }
    }

    val mainHandler = Handler(Looper.getMainLooper())

    inline fun <reified T> requestAsync(request: Request, typeToken: Type): Promise<T, Exception, Double> {
        val okHttpClient = OkHttpClient()
        val gson = GsonBuilder().create()

        val deferred = DeferredObject<T, Exception, Double>()
        okHttpClient.newCall(request).promise().then { okhttpResponse ->

            val strResult = okhttpResponse.body()?.string()
            try {
                val result = gson.fromJson<T>(strResult, typeToken)
                if (okhttpResponse.isSuccessful) {
                    mainHandler.post {
                        deferred.resolve(result)
                    }
                } else {
                    mainHandler.post {
                        deferred.reject(Exception(strResult))
                    }
                }
            } catch (e: Exception) {
                mainHandler.post {
                    deferred.reject(e)
                }
            }

        }.fail {
            deferred.reject(it)
        }
        return deferred.promise()
    }
}

fun Call.promise(): Promise<Response, Exception, Double> {
    val deferred = DeferredObject<Response, Exception, Double>()
    this.enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            deferred.resolve(response)
        }

        override fun onFailure(call: Call, e: IOException) {
            deferred.reject(e)
        }
    })
    return deferred.promise()
}
