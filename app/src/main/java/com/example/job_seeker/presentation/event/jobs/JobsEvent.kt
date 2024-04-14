package com.example.job_seeker.presentation.event.jobs


sealed class JobsEvent {
    data object GetJobs : JobsEvent()
//    data object GetUserJobs : JobsEvent()
//    data object ResetErrorMessage : JobsEvent()
//    data class AddUserJob(val job: Job) : JobsEvent()
//    data class DeleteUserJob(val userUid: String, val jobId: String) : JobsEvent()
}