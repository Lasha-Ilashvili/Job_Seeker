package com.example.job_seeker.data.repository.jobs

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.job_seeker.data.data_source.jobs.JobsPagingSource
import com.example.job_seeker.domain.model.jobs.GetJob
import com.example.job_seeker.domain.repository.jobs.JobsRepository
import kotlinx.coroutines.flow.Flow


class JobsRepositoryImpl(
    private val jobsPagingSource: JobsPagingSource
) : JobsRepository {

    override fun getJobs(): Flow<PagingData<GetJob>> {
        return Pager(PagingConfig(pageSize = 6)) {
            jobsPagingSource
        }.flow
    }
}