package com

import android.app.Application
import com.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HotRedditSubmissions : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HotRedditSubmissions)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    repositoryModule,
                    runtimeModule,
                    useCaseModule,
                    applicationModule,
                    mapperModule
                )
            )
        }
    }
}