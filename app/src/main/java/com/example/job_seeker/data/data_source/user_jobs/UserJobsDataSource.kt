package com.example.job_seeker.data.data_source.user_jobs

import com.example.job_seeker.data.model.user_jobs.UserJobDto
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class UserJobsDataSource(
    private val fireStore: FirebaseFirestore
) {
    fun addUserJob(job: UserJobDto): Task<DocumentReference> {
        return fireStore.collection("user_jobs").add(job)
    }

    fun getUserJobs(): Task<QuerySnapshot> {
        return fireStore.collection("user_jobs").get()
    }

    fun deleteUserJob(documentId: String): Task<Unit> {
        return fireStore.collection("user_jobs").document(documentId).delete().continueWith {}
    }
}