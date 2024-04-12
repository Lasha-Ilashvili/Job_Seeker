package com.example.job_seeker.data.repository.user_jobs

import com.example.job_seeker.data.common.HandleResponse
import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.data.data_source.user_jobs.UserJobsDataSource
import com.example.job_seeker.data.mapper.base.asResource
import com.example.job_seeker.data.mapper.user_jobs.toData
import com.example.job_seeker.data.mapper.user_jobs.toDomain
import com.example.job_seeker.data.model.user_jobs.UserJobDto
import com.example.job_seeker.domain.model.user_jobs.GetUserJob
import com.example.job_seeker.domain.repository.user_jobs.UserJobsRepository
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow

class UserJobsRepositoryImpl(
    private val userJobsDataSource: UserJobsDataSource,
    private val handleResponse: HandleResponse
) : UserJobsRepository {

    override suspend fun addUserJob(userJob: GetUserJob): Flow<Resource<Unit>> {
        return handleResponse.safeFireBaseCall {
            userJobsDataSource.addUserJob(userJob.toData())
        }
    }

    override suspend fun getUserJobs(userUid: String): Flow<Resource<List<GetUserJob>>> {
        return handleResponse.safeFireBaseCall {
            userJobsDataSource.getUserJobs(userUid = userUid)
        }.asResource { querySnapshot ->
            querySnapshot.documents.map {
                val dto = it.toObject<UserJobDto>() ?: UserJobDto()

                dto.toDomain()
            }
        }
    }

    override suspend fun deleteUserJob(userUid: String, jobId: String): Flow<Resource<Unit>> {
        return handleResponse.safeFireBaseCall {
            userJobsDataSource.deleteUserJob(userUid = userUid, jobId = jobId)
        }
    }
}