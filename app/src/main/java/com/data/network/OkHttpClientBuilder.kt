package com.data.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import okhttp3.OkHttpClient
import java.util.Locale
import java.util.concurrent.TimeUnit

object OkHttpClientBuilder {

    fun createOkHttp(
        appContext: Context
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
//             Would create an authenticator here to handle tokens expiring. would be passed in through constructor
//            .authenticator(authenticator)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("language", Locale.getDefault().language)
                    .method(original.method(), original.body())
                val request = requestBuilder.build()
                chain.proceed(request)
            }

        val collector = getChuckCollector(appContext)
        val interceptor = getInterceptor(appContext, collector)
        builder.addInterceptor(interceptor)

        return builder.build()
    }
}

fun getChuckCollector(context: Context): ChuckerCollector {
    return ChuckerCollector(
        context,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )
}

fun getInterceptor(context: Context, collector: ChuckerCollector): ChuckerInterceptor {
    return ChuckerInterceptor.Builder(context)
        .collector(collector)
        .maxContentLength(250_000L)
        .redactHeaders("Auth-Token", "Bearer")
        .alwaysReadResponseBody(true)
        .build()
}
