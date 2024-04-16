package com.example.job_seeker.presentation.event.jobs


sealed class JobsEvent {
    data object GetJobs : JobsEvent()
}