package com.di

import com.data.mapper.ResponseErrorHandler
import org.koin.dsl.module

val mapperModule = module {
    single { ResponseErrorHandler(get()) }
}