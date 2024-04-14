package com.example.job_seeker.domain.repository.jobs

import androidx.paging.PagingData
import com.example.job_seeker.domain.model.jobs.GetJob
import kotlinx.coroutines.flow.Flow


interface JobsRepository {
    suspend fun getJobs(): Flow<PagingData<GetJob>>
}