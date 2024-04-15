package com.example.job_seeker.presentation.event.job


sealed class JobEvent {
    data class GetJob(val jobId: String) : JobEvent()
    data object ResetErrorMessage : JobEvent()
}