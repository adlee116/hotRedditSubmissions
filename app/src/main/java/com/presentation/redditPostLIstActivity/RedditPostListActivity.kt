package com.presentation.redditPostLIstActivity

import android.os.Bundle
import com.example.hotredditsubmissions.databinding.RedditPostListActivityBinding
import com.presentation.base.BaseActivity

class RedditPostListActivity : BaseActivity() {

    private lateinit var binding: RedditPostListActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RedditPostListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}