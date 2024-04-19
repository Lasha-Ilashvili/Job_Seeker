package com.example.job_seeker.presentation.state.job

import com.example.job_seeker.presentation.model.jobs.Job


data class JobState(
    val isLoading: Boolean = false,
    val userJobExists: Boolean? = null,
    val jobApplicants: Int? = null,
    val data: Job? = null,
    val addUserJobMessage: Int? = null,
    val errorMessage: String? = null
)