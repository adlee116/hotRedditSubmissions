package com.di

import com.presentation.redditPostLIstActivity.RedditPostListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { RedditPostListViewModel(get()) }
}
