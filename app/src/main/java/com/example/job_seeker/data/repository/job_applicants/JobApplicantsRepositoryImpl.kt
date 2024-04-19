package com.example.job_seeker.data.repository.job_applicants

import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.data.data_source.job_applicants.JobApplicantsDataSource
import com.example.job_seeker.domain.repository.job_applicants.JobApplicantsRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch

class JobApplicantsRepositoryImpl(
    private val jobApplicantsDataSource: JobApplicantsDataSource
) : JobApplicantsRepository {

    override fun updateJobApplicants(jobId: String, userUid: String) {
        jobApplicantsDataSource.updateJobApplicants(jobId = jobId, userUid = userUid)
    }

    override fun getJobApplicants(jobId: String): Flow<Resource<Long>> {
        return callbackFlow {
            jobApplicantsDataSource.getJobApplicants(jobId = jobId).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    trySend(Resource.Success(data = snapshot.childrenCount))
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(Resource.Error(errorMessage = error.message))
                }
            })
            awaitClose {}
        }.catch {
            emit(Resource.Error(errorMessage = (it as Exception).message ?: ""))
        }
    }
}