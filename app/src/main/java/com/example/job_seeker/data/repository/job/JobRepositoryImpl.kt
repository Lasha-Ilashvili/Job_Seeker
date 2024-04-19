package com.example.job_seeker.data.repository.job

import com.example.job_seeker.data.common.HandleResponse
import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.data.mapper.base.asResource
import com.example.job_seeker.data.mapper.jobs.toDomain
import com.example.job_seeker.data.service.job.JobService
import com.example.job_seeker.domain.model.jobs.GetJob
import com.example.job_seeker.domain.repository.job.JobRepository
import kotlinx.coroutines.flow.Flow


class JobRepositoryImpl(
    private val jobService: JobService,
    private val handleResponse: HandleResponse
) : JobRepository {

    override fun getJob(jobId: String): Flow<Resource<GetJob>> {
        return handleResponse.safeApiCall {
            jobService.getJob(jobId = jobId)
        }.asResource { dto ->
            dto.jobItems.first().toDomain()
        }
    }
}