package com.example.job_seeker.presentation.event.job

import com.example.job_seeker.presentation.model.jobs.Job


sealed class JobEvent {
    data class GetJob(val jobId: String) : JobEvent()
    data class AddUserJob(val job: Job) : JobEvent()
    data object ResetErrorMessage : JobEvent()
    data object ResetAddUserJobMessage : JobEvent()
}