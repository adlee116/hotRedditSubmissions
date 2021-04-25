package com.presentation.redditPostLIstActivity

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
    lateinit var adapter: RedditPostAdapter
    var isLoading: Boolean = false
    private lateinit var layoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RedditPostListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
        setObservers()
        viewModel.getRedditFirstPagePosts()
        attachScrollListener()
    }

    private fun setAdapter() {
        adapter = RedditPostAdapter(postClickListeners)
        layoutManager = LinearLayoutManager(binding.root.context)
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                layoutManager.scrollToPositionWithOffset(positionStart, 0)
            }
        })
        binding.redditPostList.layoutManager = layoutManager
        binding.redditPostList.adapter = adapter
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
            adapter.updateItems(it)
            isLoading = false
        }
        viewModel.redditPostsFailure.safelyObserve(lifecycleOwner) {
            toast(it)
        }
    }

    private val postClickListeners = object : RedditPostViewHolder.PostClickListeners {
        override fun postClicked(redditPost: RedditPost, position: Int) {
            toast("This would expand the post")
        }
    }

}