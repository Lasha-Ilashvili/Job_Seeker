package com.example.job_seeker.presentation.event.user_jobs


sealed class UserJobsEvent {
    data class DeleteUserJob(val jobId: String) : UserJobsEvent()
    data object GetUserJobs : UserJobsEvent()
    data object ResetErrorMessage : UserJobsEvent()
}