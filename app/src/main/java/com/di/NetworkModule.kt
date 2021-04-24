package com.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.data.network.OkHttpClientBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single<Gson> { GsonBuilder().create() }
    single { provideOkHttpClient(androidContext()) }
    single { provideRetrofit(get(), get()) }

}

fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
    //TODO Move this to an environment class where we would point to different env dependent on build type. Staging / live etc
    return Retrofit.Builder().baseUrl("www.reddit.com")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson)).build()
}

fun provideOkHttpClient(androidContext: Context): OkHttpClient {
    return OkHttpClientBuilder.createOkHttp(androidContext)
}