package br.com.fiap.locawebmailapp.utils.api

import br.com.fiap.locawebmailapp.model.ai.GeminiRequest
import br.com.fiap.locawebmailapp.model.ai.GeminiResponse
import br.com.fiap.locawebmailapp.service.GeminiFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


suspend fun callGemini(key: String, request: GeminiRequest): GeminiResponse {

    val callGeminiService = GeminiFactory().getGeminiFactory().getResponseFromGemini(key, request)

    return suspendCoroutine { continuation ->
        callGeminiService.enqueue(object : Callback<GeminiResponse> {
            override fun onResponse(call: Call<GeminiResponse>, response: Response<GeminiResponse>) {
                response.body()?.let {
                    continuation.resume(it)
                } ?: continuation.resumeWithException(Throwable(response.errorBody()!!.string()))
            }

            override fun onFailure(call: Call<GeminiResponse>, t: Throwable) {
                continuation.resumeWithException(Throwable(t.stackTraceToString()))
            }
        })
    }
}













