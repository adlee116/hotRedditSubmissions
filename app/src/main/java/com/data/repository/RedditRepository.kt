package com.data.repository

import com.data.component.network.RedditApi
import com.data.entity.response.RedditPostResponse
import com.data.mapper.RedditPostResponseMapper
import com.domain.repository.RedditRepositoryInterface

class RedditRepository(
    private val redditApi: RedditApi,
    private val redditPostResponseMapper: RedditPostResponseMapper
) : RedditRepositoryInterface {

    override suspend fun getHotRedditPosts(after: String?): RedditPostResponse {
        return redditPostResponseMapper.map(
            redditApi.getHotSubmissions(
                after = after
            )
        )
    }

}