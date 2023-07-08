package com.junfirdaus.disneyhotstar.dashboard.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.junfirdaus.disneyhotstar.R
import com.junfirdaus.disneyhotstar.core.domain.model.GenresModel
import com.junfirdaus.disneyhotstar.core.domain.model.MoviesModel
import com.junfirdaus.disneyhotstar.databinding.ItemGenreBinding

class GenreMovieAdapter : RecyclerView.Adapter<GenreMovieAdapter.ListViewHolder>() {

    private var listDataGenre = ArrayList<GenresModel>()
    private var listDataMovie = ArrayList<MoviesModel>()
    var onItemGenreClick: ((GenresModel) -> Unit)? = null
    var onItemMovieClick: ((MoviesModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listGenre: List<GenresModel>?, listMovie: List<MoviesModel>?) {
        if (listGenre == null || listMovie == null) return
        listDataGenre.clear()
        listDataMovie.clear()

        listDataGenre.addAll(listGenre)
        listDataMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        )

    override fun getItemCount() = listDataGenre.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val dataGenre = listDataGenre[position]
        val dataMovies = listDataMovie.filter { it.genre == dataGenre.id }
        holder.bind(dataGenre, dataMovies)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemGenreBinding.bind(itemView)
        private val moviesAdapter = MoviesAdapter()

        fun bind(dataGenre: GenresModel, dataMovies: List<MoviesModel>) {
            moviesAdapter.setData(dataMovies)
            with(binding) {
                tvGenre.text = dataGenre.name
                rvMovies.adapter = moviesAdapter
            }
        }

        init {
            moviesAdapter.onItemClick = { item ->
                onItemMovieClick?.invoke(item)
            }

            binding.tvGenre.setOnClickListener {
                onItemGenreClick?.invoke(listDataGenre[absoluteAdapterPosition])
            }
        }
    }
}