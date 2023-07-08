package com.junfirdaus.disneyhotstar.dashboard.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.junfirdaus.disneyhotstar.R
import com.junfirdaus.disneyhotstar.core.BuildConfig
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviebygenre.MoviesItem
import com.junfirdaus.disneyhotstar.databinding.ItemMovieGenreBinding

class MoviesByGenreAdapter :
    PagingDataAdapter<MoviesItem, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    var onItemClick: ((MoviesItem) -> Unit)? = null

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<MoviesItem>() {
            override fun areItemsTheSame(
                oldItem: MoviesItem,
                newItem: MoviesItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MoviesItem,
                newItem: MoviesItem
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_genre, parent, false)
        )

    inner class NotificationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMovieGenreBinding.bind(itemView)

        fun bind(data: MoviesItem?) {
            Glide.with(itemView.context)
                .load("${BuildConfig.IMAGE}${data?.posterPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_image))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.itemIvMovie)

            binding.root.setOnClickListener {
                if (data != null) {
                    onItemClick?.invoke(data)
                }
            }
        }

    }
}