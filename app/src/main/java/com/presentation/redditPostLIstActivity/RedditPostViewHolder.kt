package com.presentation.redditPostLIstActivity

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.domain.models.RedditPost
import com.example.hotredditsubmissions.databinding.RedditPostCardsBinding

class RedditPostViewHolder(private val binding: RedditPostCardsBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(space: RedditPost, clickListener: PostClickListeners, position: Int) {

        binding.root.setOnClickListener { clickListener.postClicked(space, position) }
    }

    private fun loadImage(image: String, location: ImageView) {
        Glide.with(binding.root.context)
            .load(image)
            .centerCrop()
            .fitCenter()
            .into(location)
    }

    interface PostClickListeners {
        fun postClicked(spaceItem: RedditPost, position: Int)
    }
}