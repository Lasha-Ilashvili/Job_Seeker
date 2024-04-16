package com.example.job_seeker.domain.usecase.user_jobs

import com.example.job_seeker.domain.model.user_jobs.GetUserJob
import com.example.job_seeker.domain.repository.user_jobs.UserJobsRepository
import javax.inject.Inject


class AddUserJobUseCase @Inject constructor(
    private val userJobsRepository: UserJobsRepository
) {
    suspend operator fun invoke(userUid: String, userJob: GetUserJob) =
        userJobsRepository.addUserJob(userUid = userUid, userJob = userJob)
}