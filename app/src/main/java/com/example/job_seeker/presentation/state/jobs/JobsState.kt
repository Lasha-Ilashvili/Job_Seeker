package com.example.job_seeker.presentation.state.jobs

import com.example.job_seeker.presentation.model.jobs.Job


data class JobsState(
    val data: List<Job>? = null,
    val errorMessage: String? = null
)