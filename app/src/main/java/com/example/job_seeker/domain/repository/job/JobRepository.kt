package com.example.job_seeker.domain.repository.job

import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.domain.model.jobs.GetJob
import kotlinx.coroutines.flow.Flow


interface JobRepository {
    fun getJob(jobId: String): Flow<Resource<GetJob>>
}