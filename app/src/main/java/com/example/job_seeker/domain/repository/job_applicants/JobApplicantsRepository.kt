package com.example.job_seeker.domain.repository.job_applicants


interface JobApplicantsRepository {
    suspend fun updateJobApplicants(userUid: String, jobId: String)
    suspend fun getJobApplicants(userUid: String, jobId: String)
}