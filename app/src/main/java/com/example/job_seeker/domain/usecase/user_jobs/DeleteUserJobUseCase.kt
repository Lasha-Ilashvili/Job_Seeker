package com.example.job_seeker.domain.usecase.user_jobs

import com.example.job_seeker.domain.repository.user_jobs.UserJobsRepository
import javax.inject.Inject


class DeleteUserJobUseCase @Inject constructor(
    private val userJobsRepository: UserJobsRepository
) {
    suspend operator fun invoke(documentId: String?) =
        userJobsRepository.deleteUserJob(documentId = documentId ?: "")
}