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
            querySnapshot.documents.map { documentSnapshot ->
                UserJobDto(
                    id = documentSnapshot.getString("id") ?: "",
                    documentId = documentSnapshot.id,
                    title = documentSnapshot.getString("title") ?: "",
                    company = documentSnapshot.getString("company") ?: "",
                    date = documentSnapshot.getString("date") ?: "",
                    category = documentSnapshot.getString("category"),
                    contractType = documentSnapshot.getString("contractType"),
                    contractTime = documentSnapshot.getString("contractTime"),
                    salary = documentSnapshot.getString("salary") ?: "",
                    description = documentSnapshot.getString("description") ?: "",
                    location = documentSnapshot.getString("location") ?: "",
                    redirectUrl = documentSnapshot.getString("redirectUrl") ?: "",
                    latitude = documentSnapshot.getDouble("latitude"),
                    longitude = documentSnapshot.getDouble("longitude")
                ).toDomain()
            }
        }
    }

    override suspend fun deleteUserJob(documentId: String): Flow<Resource<Unit>> {
        return handleResponse.safeFireBaseCall {
            userJobsDataSource.deleteUserJob(documentId)
        }
    }
}