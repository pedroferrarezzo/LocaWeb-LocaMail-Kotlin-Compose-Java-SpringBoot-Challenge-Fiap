package br.com.fiap.locawebmailapp.service

import br.com.fiap.locawebmailapp.adapter.ByteArrayBase64Adapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


class LocaMailApiFactory {
    private val URL = "https://locamailapi.ferrarezzo.com.br/"
    val nullOnEmptyConverterFactory = object : Converter.Factory() {
        fun converterFactory() = this
        override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object : Converter<ResponseBody, Any?> {
            val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
            override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
        }
    }

    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(ByteArray::class.java, ByteArrayBase64Adapter())
        .setLenient()
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
        .addConverterFactory(nullOnEmptyConverterFactory)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun getLocaMailApiFactory(): LocaMailApiService {
        return locaMailApiFactory.create(LocaMailApiService::class.java)
    }
}