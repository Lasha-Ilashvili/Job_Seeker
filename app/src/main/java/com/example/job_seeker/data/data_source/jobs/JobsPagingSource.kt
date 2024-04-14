package com.example.job_seeker.data.data_source.jobs

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.job_seeker.data.mapper.jobs.toDomain
import com.example.job_seeker.data.service.jobs.JobsService
import com.example.job_seeker.domain.model.jobs.GetJob

class JobsPagingSource(
    private val service: JobsService,
    private val country: String,
    private val size: Int
) : PagingSource<Int, GetJob>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetJob> {
        val page = params.key ?: 1

        return try {
            val response = service.getJobs(
                country = country,
                page = page,
                size = size
            )
            val body = response.body()

            if (response.isSuccessful && body != null) {
                LoadResult.Page(
                    data = body.toDomain(),
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (body.jobItems.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Throwable(response.message()))
            }
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GetJob>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage =
                state.closestPageToPosition(anchorPosition)

            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}