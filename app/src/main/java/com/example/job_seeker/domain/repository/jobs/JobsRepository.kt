package com.example.job_seeker.domain.repository.jobs

import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.domain.model.jobs.GetJob
import kotlinx.coroutines.flow.Flow


interface JobsRepository {
    suspend fun getJobs(): Flow<Resource<List<GetJob>>>
}