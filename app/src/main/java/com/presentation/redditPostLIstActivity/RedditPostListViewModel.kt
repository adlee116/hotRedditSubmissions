package com.presentation.redditPostLIstActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.entity.response.PostData
import com.domain.models.RedditPost
import com.domain.usecase.GetRedditHotPostsUseCase

class RedditPostListViewModel(private val getRedditHotPostsUseCase: GetRedditHotPostsUseCase) : ViewModel() {

    var before: String? = null
    var after: String? = null

    private val _redditPostsResponse = MutableLiveData<List<RedditPost>>()
    val redditPostsResponse: LiveData<List<RedditPost>> get() = _redditPostsResponse

    private val _redditPostsFailure = MutableLiveData<String>()
    val redditPostsFailure: LiveData<String> get() = _redditPostsFailure

    fun getRedditFirstPagePosts() {
        getPosts(null)
    }

    fun getRedditPostsNextPage() {
        getPosts(after)
    }

    private fun getPosts(nextPageIdentifier: String?) {
        getRedditHotPostsUseCase.invoke(viewModelScope, nextPageIdentifier) { result ->
            result.result(
                onSuccess = {
                    it.data?.let { postData ->
                        _redditPostsResponse.value = createUsableModels(postData)
                        before = postData.before
                        after = postData.after
                    }
                },
                onFailure = {
                    _redditPostsFailure.value = it.message
                }
            )
        }
    }

    private fun createUsableModels(postData: PostData): List<RedditPost> {
        val postList = mutableListOf<RedditPost>()
        postData.children?.forEach {
            val data = it.data
            data?.let { redditPostContent ->
                //TODO this adds all posts, but would have some safety around this and just remove ones without
                // key data, like title etc, but for now, will just leave these as they are as I don't think we will
                // encounter this issue
                postList.add(
                    RedditPost(
                        redditPostContent.title ?: "",
                        redditPostContent.score ?: "",
                        redditPostContent.thumbnail ?: "",
                        redditPostContent.author?: ""
                    )
                )
            }
        }
        return postList
    }

}