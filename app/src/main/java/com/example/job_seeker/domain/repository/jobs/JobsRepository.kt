package com.example.job_seeker.domain.repository.jobs

import com.example.job_seeker.data.common.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody


interface JobsRepository {
    suspend fun getJobs(): Flow<Resource<ResponseBody>>
}