package com.presentation.redditPostLIstActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.domain.models.RedditPost
import com.example.hotredditsubmissions.databinding.RedditPostCardsBinding

class RedditPostAdapter(private val onClickListeners: RedditPostViewHolder.PostClickListeners) : RecyclerView.Adapter<RedditPostViewHolder>() {

    private val items = mutableListOf<RedditPost>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditPostViewHolder {
        val binding = RedditPostCardsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RedditPostViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RedditPostViewHolder, position: Int) {
        holder.bind(items[position], onClickListeners, position)
    }

    fun updateItems(newItems: List<RedditPost>) {
//        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}