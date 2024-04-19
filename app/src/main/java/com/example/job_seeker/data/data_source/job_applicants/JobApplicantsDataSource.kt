package com.example.job_seeker.data.data_source.job_applicants

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue


class JobApplicantsDataSource(
    private val database: DatabaseReference
) {
    fun updateJobApplicants(jobId: String) {
        database.child("jobs/$jobId/applicants").runTransaction(object : Transaction.Handler {

            override fun doTransaction(currentData: MutableData): Transaction.Result {
                val currentValue = currentData.getValue<Int>() ?: 0
                currentData.value = currentValue + 1
                return Transaction.success(currentData)
            }

            override fun onComplete(e: DatabaseError?, committed: Boolean, curr: DataSnapshot?) {}
        })
    }

    fun getJobApplicants(jobId: String) {
//        callbackFlow {
        database.child("jobs/$jobId/applicants").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                if (!snapshot.exists()) {
//                    setApplicants(jobId = jobId)
//                    return
//                }

                val applicants = snapshot.getValue<Int>()

                if (applicants != null)
                    println(applicants)
                else
                    setApplicants(jobId = jobId)

//                trySend(k)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
//        }
    }

    private fun setApplicants(jobId: String, value: Int = 0) {
        database.child("jobs/$jobId/applicants").setValue(value)
    }
}
