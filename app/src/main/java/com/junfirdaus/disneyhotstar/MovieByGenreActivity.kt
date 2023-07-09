package com.junfirdaus.disneyhotstar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.junfirdaus.disneyhotstar.core.data.Resource
import com.junfirdaus.disneyhotstar.core.utils.getData
import com.junfirdaus.disneyhotstar.dashboard.DashboardViewModel
import com.junfirdaus.disneyhotstar.dashboard.MovieDetailDialogFragment
import com.junfirdaus.disneyhotstar.dashboard.ui.MoviesByGenreAdapter
import com.junfirdaus.disneyhotstar.databinding.ActivityMovieByGenreBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MovieByGenreActivity : AppCompatActivity() {

    private val dashboardViewModel: DashboardViewModel by viewModel()
    private lateinit var binding: ActivityMovieByGenreBinding

    private var idGenre = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieByGenreBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(binding.root)

        idGenre = intent.getData(ID)
        getMoviesByGenre(idGenre)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getMoviesByGenre(id: Int) {
        val moviesByGenreAdapter = MoviesByGenreAdapter()
        moviesByGenreAdapter.onItemClick = { item ->
            val movieDetailDialogFragment = MovieDetailDialogFragment()
            movieDetailDialogFragment.setIdMovieDetailDialogFragment(item.id.toString())
            movieDetailDialogFragment.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.AppBottomSheetDialogTheme
            )
            movieDetailDialogFragment.show(supportFragmentManager, "detail_movie")
        }
        lifecycleScope.launch {
            dashboardViewModel.getMoviesByGenre(genreId = id).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Resource.Success -> {
                        if (it.data != null) {
                            lifecycleScope.launch {
                                moviesByGenreAdapter.loadStateFlow.collectLatest { loadState ->
                                    binding.progressBar.isVisible =
                                        loadState.refresh is LoadState.Loading
                                }
                            }
                            moviesByGenreAdapter.submitData(it.data!!)

                        }
                    }
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                    }
                    else -> {}
                }
            }
        }

        with(binding.rvMovies) {
            this.setHasFixedSize(true)
            this.adapter = moviesByGenreAdapter
        }
    }

    companion object {
        const val ID = "id"
    }
}