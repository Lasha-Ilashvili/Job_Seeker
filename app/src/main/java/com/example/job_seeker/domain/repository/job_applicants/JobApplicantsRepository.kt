package com.example.job_seeker.domain.repository.job_applicants

import com.example.job_seeker.data.common.Resource
import kotlinx.coroutines.flow.Flow


interface JobApplicantsRepository {
    fun updateJobApplicants(jobId: String, userUid:String)
    fun getJobApplicants(jobId: String): Flow<Resource<Long>>
}