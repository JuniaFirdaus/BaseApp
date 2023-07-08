package com.junfirdaus.disneyhotstar.core.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.junfirdaus.disneyhotstar.core.data.source.remote.network.ApiService
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviebygenre.MoviesItem
import retrofit2.HttpException
import java.io.IOException

class MoviesByGenrePagingSource(
    private val id: Int,
    private val apiService: ApiService,
) : PagingSource<Int, MoviesItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesItem> {
        val nextPageNumber = params.key ?: 1
        return try {
            val response = apiService.getMovies(
                genre = id,
                page = nextPageNumber,
            )

            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber,
                nextKey = if (response.totalPages == nextPageNumber) null else nextPageNumber + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MoviesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
