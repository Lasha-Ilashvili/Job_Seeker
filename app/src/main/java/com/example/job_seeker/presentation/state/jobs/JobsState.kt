package com.example.job_seeker.presentation.state.jobs

import androidx.paging.PagingData
import com.example.job_seeker.presentation.model.jobs.Job


data class JobsState(
    val jobs: PagingData<Job>? = null
)