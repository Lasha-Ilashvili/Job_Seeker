package com.example.job_seeker.data.repository.jobs

import com.example.job_seeker.data.common.HandleResponse
import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.data.mapper.base.asResource
import com.example.job_seeker.data.mapper.jobs.toDomain
import com.example.job_seeker.data.service.jobs.JobsService
import com.example.job_seeker.domain.model.jobs.GetJob
import com.example.job_seeker.domain.repository.jobs.JobsRepository
import kotlinx.coroutines.flow.Flow


class JobsRepositoryImpl(
    private val jobsService: JobsService,
    private val handleResponse: HandleResponse
) : JobsRepository {

    override suspend fun getJobs(): Flow<Resource<List<GetJob>>> {
        return handleResponse.safeApiCall {
            jobsService.getJobs(country = "gb", page = 1, size = 10)
        }.asResource { dto ->
            dto.toDomain()
        }
    }
}