package com.example.job_seeker.data.data_source.job_applicants

import com.google.firebase.database.DatabaseReference


class JobApplicantsDataSource(
    private val database: DatabaseReference
) {

    fun updateJobApplicants(jobId: String, userUid: String) {
        database.child("jobs/$jobId/applicants").child(userUid).setValue(true)
    }

    fun getJobApplicants(jobId: String): DatabaseReference {
        return database.child("jobs/$jobId/applicants")
    }
}
