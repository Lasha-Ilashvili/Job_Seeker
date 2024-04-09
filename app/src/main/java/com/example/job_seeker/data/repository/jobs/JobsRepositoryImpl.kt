package com.example.job_seeker.data.repository.jobs

import com.example.job_seeker.data.common.HandleResponse
import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.data.service.jobs.JobsService
import com.example.job_seeker.domain.repository.jobs.JobsRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject


class JobsRepositoryImpl @Inject constructor(
    private val jobsService: JobsService,
    private val handleResponse: HandleResponse
) : JobsRepository {

    override suspend fun getJobs(): Flow<Resource<ResponseBody>> {
        return handleResponse.safeApiCall {
            jobsService.getJobs(country = "gb", page = 1, size = 10)
        }
    }
}