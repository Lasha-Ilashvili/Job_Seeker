package com.example.job_seeker.presentation.state.user_jobs

import com.example.job_seeker.presentation.model.user_jobs.UserJob


data class UserJobsState(
    val isLoading: Boolean = false,
    val data: List<UserJob>? = null,
    val errorMessage: String? = null,
)