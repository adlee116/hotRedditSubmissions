package com.data.mapper

import com.data.entity.response.RedditPostResponse
import retrofit2.Response

class RedditPostResponseMapper(
    private val errorResponseHandler: ResponseErrorHandler
) {
    fun map(response: Response<RedditPostResponse>): RedditPostResponse {
        return when {
            response.isSuccessful && response.errorBody() == null -> responseToRedditPostResult(response)
            else -> RedditPostResponse(errorResponse = errorResponseHandler.map(response))
        }
    }

    private fun responseToRedditPostResult(response: Response<RedditPostResponse>): RedditPostResponse {
        response.body()?.let {
            return it
        } ?: run {
            return RedditPostResponse(errorResponse = errorResponseHandler.map(response))
        }
    }
}