package com.di

import com.domain.usecase.GetRedditHotPostsUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single { GetRedditHotPostsUseCase(get()) }

}
