package com.junfirdaus.disneyhotstar.core.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.junfirdaus.disneyhotstar.core.data.source.remote.network.ApiService
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviereviwers.ReviewersItem
import retrofit2.HttpException
import java.io.IOException

class MovieReviewersPagingSource(
    private val key: String,
    private val apiService: ApiService,
) : PagingSource<Int, ReviewersItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewersItem> {
        val nextPageNumber = params.key ?: 1
        return try {
            val response = apiService.getMovieReviewers(
                id = key,
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

    override fun getRefreshKey(state: PagingState<Int, ReviewersItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
