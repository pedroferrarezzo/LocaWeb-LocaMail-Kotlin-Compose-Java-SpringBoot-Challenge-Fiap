package br.com.fiap.locawebmailapp.service

import br.com.fiap.locawebmailapp.adapter.ByteArrayBase64Adapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LocaMailApiFactory {
    private val URL = "http://192.168.1.2/"
    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(ByteArray::class.java, ByteArrayBase64Adapter())
        .create()
// APENAS PARA DESENVOLVIMENTO (LOG INTERCEPTOR OKHTTP)
    val intercepter = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder().apply {
        this.addInterceptor(intercepter)
    }.build()

    private val locaMailApiFactory = Retrofit.Builder()
        .baseUrl(URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun getLocaMailApiFactory(): LocaMailApiService {
        return locaMailApiFactory.create(LocaMailApiService::class.java)
    }
}