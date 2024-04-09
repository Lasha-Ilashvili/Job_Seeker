package com.example.job_seeker.domain.usecase.jobs

import com.example.job_seeker.domain.repository.jobs.JobsRepository
import javax.inject.Inject


class GetJobsUseCase @Inject constructor(
    private val jobsRepository: JobsRepository
) {
    suspend operator fun invoke() = jobsRepository.getJobs()
}