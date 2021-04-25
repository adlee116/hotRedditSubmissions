package com.data.component.network

import com.data.entity.response.RedditPostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {

    @GET("/r/Android/hot.json")
    suspend fun getHotSubmissions(@Query("after") after: String?
    ): Response<RedditPostResponse>
}