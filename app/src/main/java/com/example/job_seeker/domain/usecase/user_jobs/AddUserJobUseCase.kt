package com.example.job_seeker.domain.usecase.user_jobs

import com.example.job_seeker.domain.model.user_jobs.GetUserJob
import com.example.job_seeker.domain.repository.user_jobs.UserJobsRepository
import javax.inject.Inject


class AddUserJobUseCase @Inject constructor(
    private val userJobsRepository: UserJobsRepository
) {
    suspend operator fun invoke(userJob: GetUserJob) =
        userJobsRepository.addUserJob(userJob = userJob)
}