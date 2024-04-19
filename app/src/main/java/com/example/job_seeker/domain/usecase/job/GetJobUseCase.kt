package com.example.job_seeker.domain.usecase.job

import com.example.job_seeker.domain.repository.job.JobRepository
import javax.inject.Inject


class GetJobUseCase @Inject constructor(
    private val jobRepository: JobRepository
) {
    operator fun invoke(jobId: String) =
        jobRepository.getJob(jobId = jobId)
}