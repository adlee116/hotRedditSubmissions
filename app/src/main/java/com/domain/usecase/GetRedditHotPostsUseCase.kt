package com.domain.usecase

import com.core.BaseUseCase
import com.core.Result
import com.data.entity.response.RedditPostResponse
import com.domain.repository.RedditRepositoryInterface

class GetRedditHotPostsUseCase(
    private val redditAutRepositoryInterface: RedditRepositoryInterface
): BaseUseCase<RedditPostResponse, String?>() {
    override suspend fun run(params: String?): Result<RedditPostResponse, Exception> {
        return try {
            val redditPostsResponse = redditAutRepositoryInterface.getHotRedditPosts(params)
            return Result.Success(redditPostsResponse)
        } catch (ex: Exception) {
            Result.Failure(ex)
        }
    }
}