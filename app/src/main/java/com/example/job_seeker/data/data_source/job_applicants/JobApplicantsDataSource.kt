package com.example.job_seeker.data.data_source.job_applicants

import com.google.firebase.database.FirebaseDatabase


class JobApplicantsDataSource(
    private val database: FirebaseDatabase
) {
    fun updateJobApplicants(userUid: String, jobId: String) {
    }

    fun getJobApplicants(userUid: String, jobId: String) {

    }
}