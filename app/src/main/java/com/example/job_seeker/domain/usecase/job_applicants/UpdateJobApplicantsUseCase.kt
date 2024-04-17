package com.example.job_seeker.domain.usecase.job_applicants

import com.example.job_seeker.domain.repository.job_applicants.JobApplicantsRepository
import javax.inject.Inject


class UpdateJobApplicantsUseCase @Inject constructor(
    private val jobApplicantsRepository: JobApplicantsRepository
) {
    suspend operator fun invoke(userUid: String, jobId: String) =
        jobApplicantsRepository.updateJobApplicants(userUid = userUid, jobId = jobId)
}