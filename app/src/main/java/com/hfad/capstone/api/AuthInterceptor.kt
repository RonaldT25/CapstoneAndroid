package com.hfad.capstone.api

import android.content.Context
import com.hfad.capstone.helper.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("auth-token", it)
        }

        return chain.proceed(requestBuilder.build())
    }
}