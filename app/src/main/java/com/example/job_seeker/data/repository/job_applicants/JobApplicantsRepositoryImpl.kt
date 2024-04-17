package com.example.job_seeker.data.repository.job_applicants

import com.example.job_seeker.data.common.HandleResponse
import com.example.job_seeker.data.data_source.job_applicants.JobApplicantsDataSource
import com.example.job_seeker.domain.repository.job_applicants.JobApplicantsRepository

class JobApplicantsRepositoryImpl(
    private val jobApplicantsDataSource: JobApplicantsDataSource,
    private val handleResponse: HandleResponse
) : JobApplicantsRepository {

    override suspend fun updateJobApplicants(userUid: String, jobId: String) {

    }

    override suspend fun getJobApplicants(userUid: String, jobId: String) {

    }
}