package com.presentation.redditPostLIstActivity

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.domain.models.RedditPost
import com.example.hotredditsubmissions.databinding.RedditPostCardsBinding
import kotlin.math.abs

class RedditPostViewHolder(private val binding: RedditPostCardsBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(post: RedditPost, clickListener: PostClickListeners, position: Int) {
        binding.root.setOnClickListener { clickListener.postClicked(post, position) }
        binding.postTitle.text = post.title
        binding.rating.text = "Upvotes: " + intConversion(post.rating.toInt())
        binding.author.text = post.author
        loadImage(post.thumbnail, binding.postThumbnail)
    }

    private fun loadImage(image: String, location: ImageView) {
        Glide.with(binding.root.context)
            .load(image)
            .centerCrop()
            .fitCenter()
            .into(location)
    }

    interface PostClickListeners {
        fun postClicked(redditPost: RedditPost, position: Int)
    }

    private fun intConversion(number: Int): String {
        return when {
            abs(number / 1000000) > 1 -> {
                (number / 1000000).toString() + "m";

            }
            abs(number / 1000) > 1 -> {
                (number / 1000).toString() + "k";

            }
            else -> {
                number.toString();
            }
        }
    }

}