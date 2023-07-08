package com.junfirdaus.disneyhotstar.dashboard.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.junfirdaus.disneyhotstar.R
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviereviwers.ReviewersItem
import com.junfirdaus.disneyhotstar.databinding.ItemReviewBinding

class MoviesReviewerAdapter :
    PagingDataAdapter<ReviewersItem, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    var onItemClick: ((ReviewersItem) -> Unit)? = null

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<ReviewersItem>() {
            override fun areItemsTheSame(
                oldItem: ReviewersItem,
                newItem: ReviewersItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ReviewersItem,
                newItem: ReviewersItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? NotificationsViewHolder)?.bind(data = getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NotificationsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        )

    inner class NotificationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemReviewBinding.bind(itemView)

        fun bind(data: ReviewersItem?) {
            val path = data?.authorDetails?.avatarPath?.replaceFirst("/", "")
            Glide.with(itemView.context)
                .load(path)
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.cvReviewer)

            binding.tvReviewer.text = data?.authorDetails?.name

            binding.root.setOnClickListener {
                if (data != null) {
                    onItemClick?.invoke(data)
                }
            }
        }

    }
}