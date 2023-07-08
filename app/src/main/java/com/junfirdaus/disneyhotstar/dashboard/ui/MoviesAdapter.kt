package com.junfirdaus.disneyhotstar.dashboard.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.junfirdaus.disneyhotstar.R
import com.junfirdaus.disneyhotstar.core.BuildConfig
import com.junfirdaus.disneyhotstar.core.domain.model.MoviesModel
import com.junfirdaus.disneyhotstar.databinding.ItemMovieBinding

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.ListViewHolder>() {

    private var listDataMovie = ArrayList<MoviesModel>()
    var onItemClick: ((MoviesModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listMovie: List<MoviesModel>?) {
        if (listMovie == null) return
        listDataMovie.clear()

        listDataMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )

    override fun getItemCount() = listDataMovie.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listDataMovie[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieBinding.bind(itemView)

        fun bind(data: MoviesModel) {
            Glide.with(itemView.context)
                .load("${BuildConfig.IMAGE}${data.posterPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_image))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.itemIvMovie)
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listDataMovie[absoluteAdapterPosition])
            }
        }
    }
}