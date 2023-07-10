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
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.nowplaying.NowPlayingsItem
import com.junfirdaus.disneyhotstar.databinding.ItemNowPlayingBinding

class NowPlayingMoviesAdapter : RecyclerView.Adapter<NowPlayingMoviesAdapter.ListViewHolder>() {

    private var listDataMovie = ArrayList<NowPlayingsItem>()
    var onItemClick: ((NowPlayingsItem) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listMovie: List<NowPlayingsItem>?) {
        if (listMovie == null) return
        listDataMovie.clear()

        listDataMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_now_playing, parent, false)
        )

    override fun getItemCount() = listDataMovie.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listDataMovie[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNowPlayingBinding.bind(itemView)

        fun bind(data: NowPlayingsItem) {
            Glide.with(itemView.context)
                .load("${BuildConfig.IMAGE}${data.posterPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_movie))
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