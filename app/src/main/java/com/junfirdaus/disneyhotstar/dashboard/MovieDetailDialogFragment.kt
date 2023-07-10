package com.junfirdaus.disneyhotstar.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.junfirdaus.disneyhotstar.R
import com.junfirdaus.disneyhotstar.core.BuildConfig
import com.junfirdaus.disneyhotstar.core.data.Resource
import com.junfirdaus.disneyhotstar.dashboard.ui.MoviesReviewerAdapter
import com.junfirdaus.disneyhotstar.dashboard.ui.MoviesSimilarAdapter
import com.junfirdaus.disneyhotstar.databinding.FragmentDetailMovieDialogBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class MovieDetailDialogFragment : BottomSheetDialogFragment() {

    private val dashboardViewModel: DashboardViewModel by viewModel()
    private var _binding: FragmentDetailMovieDialogBinding? = null
    private val binding get() = _binding

    private var mMovieId = ""
    private var mYoutubeUrl = ""

    fun setIdMovieDetailDialogFragment(id: String) {
        this.mMovieId = id
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailMovieDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            getMovieById(mMovieId)
            getMoviesSimilar(mMovieId)
            getMovieVideos(mMovieId)
            getMoviesReviewer(mMovieId)

            binding?.btnWatch?.setOnClickListener {
                openYouTubeVideo(mYoutubeUrl)
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun openYouTubeVideo(videoId: String) {
        val youtubeAppIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
        val webIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))

        if (youtubeAppIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(youtubeAppIntent)
        } else {
            startActivity(webIntent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getMovieById(id: String) {
        dashboardViewModel.getMovieById(id)
            .observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it) {
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            binding?.let { it1 ->
                                Glide.with(this)
                                    .load("${BuildConfig.IMAGE}${it.data?.productionCompanies?.first()?.logoPath}")
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(it1.ivCompany)
                            }
                            binding?.let { it1 ->
                                Glide.with(this)
                                    .load("${BuildConfig.IMAGE}${it.data?.backdropPath}")
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(it1.ivMovie)
                            }

                            with(binding) {
                                this?.tvYear?.text =
                                    "${it.data?.releaseDate} | ${it.data?.spokenLanguages?.size} languages | ${it.data?.voteAverage} votes"
                                this?.tvGenre?.text =
                                    it.data?.genres?.map { genre -> genre?.name }.toString()
                                this?.tvOverview?.text = it.data?.overview
                            }
                        }
                        is Resource.Error -> {
                        }
                        else -> {}
                    }
                }
            }
    }

    private fun getMovieVideos(id: String) {
        dashboardViewModel.getMovieVideos(id)
            .observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it) {
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            val videos = it.data
                            val trailer =
                                videos?.find { video -> video.type == "Trailer" && video.site == "YouTube" }
                            val trailerKey = trailer?.key

                            if (!trailerKey.isNullOrEmpty()) {
                                mYoutubeUrl = "https://www.youtube.com/watch?v=$trailerKey"
                            }
                        }
                        is Resource.Error -> {
                        }
                        else -> {}
                    }
                }
            }
    }

    private fun getMoviesSimilar(id: String) {
        val moviesSimilarAdapter = MoviesSimilarAdapter()
        moviesSimilarAdapter.onItemClick = { item ->
            val movieDetailDialogFragment = MovieDetailDialogFragment()
            movieDetailDialogFragment.setIdMovieDetailDialogFragment(item.id.toString())
            movieDetailDialogFragment.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.AppBottomSheetDialogTheme
            )
            movieDetailDialogFragment.show(childFragmentManager, "detail_movie")
        }
        viewLifecycleOwner.lifecycleScope.launch {
            dashboardViewModel.getMoviesSimilar(id = id).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding?.progressBar?.isVisible = true
                    }
                    is Resource.Success -> {
                        if (it.data != null) {
                            lifecycleScope.launch {
                                moviesSimilarAdapter.loadStateFlow.collectLatest { loadState ->
                                    binding?.progressBar?.isVisible =
                                        loadState.refresh is LoadState.Loading
                                }
                            }
                            moviesSimilarAdapter.submitData(it.data!!)

                        }
                    }
                    is Resource.Error -> {
                        binding?.progressBar?.isVisible = false
                    }
                    else -> {}
                }
            }
        }

        with(binding?.rvSimilar) {
            this?.setHasFixedSize(true)
            this?.adapter = moviesSimilarAdapter
        }
    }

    private fun getMoviesReviewer(id: String) {
        val moviesReviewerAdapter = MoviesReviewerAdapter()
        viewLifecycleOwner.lifecycleScope.launch {
            dashboardViewModel.getMovieReviewers(id = id).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding?.progressBar?.isVisible = true
                    }
                    is Resource.Success -> {
                        if (it.data != null) {
                            lifecycleScope.launch {
                                moviesReviewerAdapter.loadStateFlow.collectLatest { loadState ->
                                    binding?.progressBar?.isVisible =
                                        loadState.refresh is LoadState.Loading
                                }
                            }
                            moviesReviewerAdapter.submitData(it.data!!)

                        }
                    }
                    is Resource.Error -> {
                        binding?.progressBar?.isVisible = false
                    }
                    else -> {}
                }
            }
        }

        with(binding?.rvReviewers) {
            this?.setHasFixedSize(
                true
            )
            this?.adapter = moviesReviewerAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}