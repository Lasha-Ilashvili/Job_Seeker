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
        }.asResource {}
    }

    override suspend fun getUserJobs(): Flow<Resource<List<GetUserJob>>> {
        return handleResponse.safeFireBaseCall {
            userJobsDataSource.getUserJobs()
        }.asResource { querySnapshot ->
            querySnapshot.documents.map {
                val convertedObject = it.toObject<UserJobDto>() ?: UserJobDto()

                val dto = convertedObject.copy(documentId = it.id)

                dto.toDomain()
            }
        }
    }

    override suspend fun deleteUserJob(documentId: String): Flow<Resource<Unit>> {
        return handleResponse.safeFireBaseCall {
            userJobsDataSource.deleteUserJob(documentId)
        }
    }
}