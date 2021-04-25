package com.presentation.redditPostLIstActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.domain.models.RedditPost
import com.example.hotredditsubmissions.databinding.RedditPostListActivityBinding
import com.presentation.base.BaseActivity
import com.presentation.utils.safelyObserve
import org.koin.androidx.viewmodel.ext.android.viewModel

class RedditPostListActivity : BaseActivity() {

    private lateinit var binding: RedditPostListActivityBinding
    private val viewModel: RedditPostListViewModel by viewModel()
    var isLoading: Boolean = false
    private lateinit var layoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RedditPostListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savedInstanceState?.let {
            binding.redditPostList.adapter = viewModel.adapter
        } ?: run {
            setAdapter()
            viewModel.getRedditFirstPagePosts()
        }
        setLayoutManager()
        setObservers()
        attachScrollListener()
    }

    private fun setAdapter() {
        viewModel.adapter = RedditPostAdapter(postClickListeners)
        viewModel.adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                layoutManager.scrollToPositionWithOffset(positionStart, 0)
            }
        })
        binding.redditPostList.adapter = viewModel.adapter
    }

    private fun setLayoutManager() {
        layoutManager = LinearLayoutManager(binding.root.context)
        binding.redditPostList.layoutManager = layoutManager
    }

    private fun attachScrollListener() {
        binding.redditPostList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading) {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == viewModel.postAmount - 1) {
                        viewModel.getRedditPostsNextPage()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun setObservers() {
        viewModel.redditPostsResponse.safelyObserve(lifecycleOwner) {
            viewModel.adapter.updateItems(it)
            isLoading = false
        }
        viewModel.redditPostsFailure.safelyObserve(lifecycleOwner) {
            toast(it)
        }
    }

    private val postClickListeners = object : RedditPostViewHolder.PostClickListeners {
        override fun postClicked(redditPost: RedditPost, position: Int) {
            // TODO extract this code.
            val url = "https://www.reddit.com" + redditPost.postUrl
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }

}