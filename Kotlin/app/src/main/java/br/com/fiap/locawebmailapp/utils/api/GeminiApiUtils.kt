package br.com.fiap.locawebmailapp.utils.api

import br.com.fiap.locawebmailapp.model.ai.AiQuestion
import br.com.fiap.locawebmailapp.model.ai.GeminiRequest
import br.com.fiap.locawebmailapp.model.ai.GeminiResponse
import br.com.fiap.locawebmailapp.service.GeminiFactory
import br.com.fiap.locawebmailapp.service.LocaMailApiFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


fun callGemini(key: String,
               request: GeminiRequest,
               onSuccess: (GeminiResponse?) -> Unit, onError: (Throwable) -> Unit) {

    val callGeminiService = GeminiFactory().getGeminiFactory().getResponseFromGemini(key, request)

    callGeminiService.enqueue(object : Callback<GeminiResponse?> {
        override fun onResponse(
            call: Call<GeminiResponse?>,
            response: Response<GeminiResponse?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<GeminiResponse?>, t: Throwable) {
            onError(t)
        }
    })
}













