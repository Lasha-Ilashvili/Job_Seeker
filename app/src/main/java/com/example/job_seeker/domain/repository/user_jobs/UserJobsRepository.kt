package com.example.job_seeker.domain.repository.user_jobs

import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.domain.model.user_jobs.GetUserJob
import kotlinx.coroutines.flow.Flow


interface UserJobsRepository {
    suspend fun addUserJob(userJob: GetUserJob): Flow<Resource<Unit>>
    suspend fun getUserJobs(): Flow<Resource<List<GetUserJob>>>
    suspend fun deleteUserJob(documentId: String): Flow<Resource<Unit>>
}