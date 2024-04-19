package com.example.job_seeker.domain.usecase.job_applicants

import com.example.job_seeker.domain.repository.job_applicants.JobApplicantsRepository
import javax.inject.Inject


class GetJobApplicantsUseCase @Inject constructor(
    private val jobApplicantsRepository: JobApplicantsRepository
) {
    operator fun invoke(jobId: String) =
        jobApplicantsRepository.getJobApplicants(jobId = jobId)
}