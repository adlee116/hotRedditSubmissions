package com.presentation.redditPostLIstActivity

import android.os.Bundle
import com.domain.models.RedditPost
import com.example.hotredditsubmissions.databinding.RedditPostListActivityBinding
import com.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RedditPostListActivity : BaseActivity() {

    private lateinit var binding: RedditPostListActivityBinding
    private val viewModel: RedditPostListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RedditPostListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
    }

    private fun setAdapter() {
        val adapter = RedditPostAdapter(postClickListeners)
        binding.redditPostList.adapter = adapter
    }

    private val postClickListeners = object : RedditPostViewHolder.PostClickListeners {
        override fun postClicked(spaceItem: RedditPost, position: Int) {
            toast("This would expand the property")
        }

    }

}