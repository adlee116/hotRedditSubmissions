package com.di

import com.data.repository.RedditRepository
import com.domain.repository.RedditRepositoryInterface
import org.koin.dsl.module

val repositoryModule = module {

    single<RedditRepositoryInterface> { RedditRepository(get(), get()) }

}
