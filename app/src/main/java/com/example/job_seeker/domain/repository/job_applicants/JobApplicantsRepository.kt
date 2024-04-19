package com.example.job_seeker.domain.repository.job_applicants


interface JobApplicantsRepository {
    suspend fun updateJobApplicants(jobId: String)
    suspend fun getJobApplicants(jobId: String)
}