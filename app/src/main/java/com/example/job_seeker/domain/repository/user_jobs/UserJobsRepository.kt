package com.example.job_seeker.domain.repository.user_jobs

import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.domain.model.user_jobs.GetUserJob
import kotlinx.coroutines.flow.Flow


interface UserJobsRepository {
    fun addUserJob(userUid: String, userJob: GetUserJob): Flow<Resource<Unit>>
    fun getUserJobs(userUid: String): Flow<Resource<List<GetUserJob>>>
    fun userJobExists(userUid: String, jobId: String): Flow<Resource<Boolean>>
    fun deleteUserJob(userUid: String, jobId: String): Flow<Resource<Unit>>
}