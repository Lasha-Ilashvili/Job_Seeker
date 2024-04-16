package com.example.job_seeker.data.data_source.user_jobs

import com.example.job_seeker.data.model.user_jobs.UserJobDto
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class UserJobsDataSource(
    private val fireStore: FirebaseFirestore
) {
    fun addUserJob(userUid: String, job: UserJobDto): Task<Unit> {
        return fireStore.document("users/${userUid}/jobs/${job.id}").set(job).continueWith {}
    }

    fun getUserJobs(userUid: String): Task<QuerySnapshot> {
        return fireStore.collection("users/$userUid/jobs").get()
    }

    fun deleteUserJob(userUid: String, jobId: String): Task<Unit> {
        return fireStore.document("users/$userUid/jobs/$jobId").delete().continueWith {}
    }
}