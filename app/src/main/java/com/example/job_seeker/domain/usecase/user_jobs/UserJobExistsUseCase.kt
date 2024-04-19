package com.example.job_seeker.domain.usecase.user_jobs

import com.example.job_seeker.domain.repository.user_jobs.UserJobsRepository
import javax.inject.Inject


class UserJobExistsUseCase @Inject constructor(
    private val userJobsRepository: UserJobsRepository
) {
    operator fun invoke(userUid: String, jobId: String) =
        userJobsRepository.userJobExists(userUid = userUid, jobId = jobId)
}