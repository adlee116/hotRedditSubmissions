package com.domain.repository

import com.data.entity.response.RedditPostResponse

interface RedditRepositoryInterface {

    suspend fun getHotRedditPosts(after: String?): RedditPostResponse
}