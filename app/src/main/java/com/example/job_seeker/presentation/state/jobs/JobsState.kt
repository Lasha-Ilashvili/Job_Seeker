package com.example.job_seeker.presentation.state.jobs

import com.example.job_seeker.presentation.model.jobs.Job
import com.example.job_seeker.presentation.model.user_jobs.UserJob


data class JobsState(
    val isLoading: Boolean = false,
    val jobs: List<Job>? = null,
    val userJobs: List<UserJob>? = null,
    val errorMessage: String? = null
)