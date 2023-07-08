package com.junfirdaus.disneyhotstar.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.junfirdaus.disneyhotstar.MovieByGenreActivity
import com.junfirdaus.disneyhotstar.R
import com.junfirdaus.disneyhotstar.core.data.Resource
import com.junfirdaus.disneyhotstar.core.domain.model.GenresModel
import com.junfirdaus.disneyhotstar.dashboard.ui.GenreMovieAdapter
import com.junfirdaus.disneyhotstar.databinding.ActivityDashboardBinding
import kotlinx.coroutines.*
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardActivity : AppCompatActivity() {

    private val dashboardViewModel: DashboardViewModel by viewModel()
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getGenres()
    }

    private fun getGenres() {
        dashboardViewModel.getGenres()
            .observe(this) {
                if (it != null) {
                    when (it) {
                        is Resource.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                        is Resource.Success -> {
                            binding.progressBar.isVisible = false
                            getMovies(genreItem = it.data ?: emptyList())
                        }
                        is Resource.Error -> {
                            binding.progressBar.isVisible = false
                        }
                        else -> {}
                    }
                }
            }
    }

    private fun getMovies(genreItem: List<GenresModel>) {
        val genreMovieAdapter = GenreMovieAdapter()
        genreMovieAdapter.onItemGenreClick = { item ->
            val intent = Intent(this, MovieByGenreActivity::class.java)
            intent.putExtra("id", item.id)
            startActivity(intent)
        }

        genreMovieAdapter.onItemMovieClick = { item ->
            val movieDetailDialogFragment = MovieDetailDialogFragment()
            movieDetailDialogFragment.movieDetailDialogFragment(item.idMovie.toString())
            movieDetailDialogFragment.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.AppBottomSheetDialogTheme
            )
            movieDetailDialogFragment.show(supportFragmentManager, "detail_movie")
        }

        lifecycleScope.launch(Dispatchers.Main) {
            coroutineScope {
                val deferredList = genreItem.map { item ->
                    async(Dispatchers.IO) {
                        dashboardViewModel.getMovies(item.id)
                    }
                }
                val results = deferredList.awaitAll()
                for (result in results) {
                    result.observe(this@DashboardActivity) {
                        if (it != null) {
                            when (it) {
                                is Resource.Loading -> {
                                    binding.progressBar.isVisible = true
                                }
                                is Resource.Success -> {
                                    genreMovieAdapter.setData(
                                        listGenre = genreItem,
                                        listMovie = it.data
                                    )
                                    binding.progressBar.isVisible = false
                                }
                                is Resource.Error -> {
                                    binding.progressBar.isVisible = false
                                }
                                else -> {}
                            }
                        }
                    }
                }
            }
        }
        with(binding.rvGenre) {
            this.layoutManager =
                LinearLayoutManager(this@DashboardActivity)
            this.setHasFixedSize(true)
            this.adapter = genreMovieAdapter
        }
    }
}