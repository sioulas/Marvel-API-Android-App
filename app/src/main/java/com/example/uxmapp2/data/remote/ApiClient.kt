package com.example.uxmapp2.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.uxmapp2.source.Constants
import java.util.concurrent.TimeUnit

object ApiClient {
    private var retrofit: Retrofit? = null
    private var client: OkHttpClient? = null
    private val connectionPool = okhttp3.ConnectionPool(0, 1, TimeUnit.NANOSECONDS)

    fun getClient(): Retrofit {
        if (retrofit == null) {
            val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .connectionPool(connectionPool)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client!!)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun shutdown() {
        client?.dispatcher?.executorService?.shutdown()
        client?.dispatcher?.executorService?.shutdownNow()
        connectionPool.evictAll()
        client?.connectionPool?.evictAll()
        client = null
        retrofit = null
    }
}




