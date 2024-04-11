package com.example.job_seeker.data.common

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import retrofit2.Response

class HandleResponse() {
    fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loading(loading = true))

        try {
            val response = call()
            val body = response.body()

            if (response.isSuccessful && body != null) {
                emit(Resource.Loading(loading = false))
                emit(Resource.Success(data = body))
            } else {
                emit(Resource.Loading(loading = false))
                emit(Resource.Error(errorMessage = response.errorBody()?.string() ?: ""))
            }

        } catch (e: Throwable) {
            emit(Resource.Loading(loading = false))
            emit(Resource.Error(errorMessage = e.message ?: ""))
        }
    }

    fun <T : Any> safeFireBaseCall(call: () -> Task<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading(loading = true))

        try {
            val task = call()
            val result = task.await()

            if (task.isSuccessful) {
                emit(Resource.Loading(false))
                emit(Resource.Success(data = result))
            } else {
                emit(Resource.Loading(false))
                emit(Resource.Error(errorMessage = task.exception?.localizedMessage ?: ""))
            }

        } catch (e: FirebaseAuthException) {
            emit(Resource.Loading(false))
            emit(Resource.Error(errorMessage = e.message ?: ""))
        } catch (e: Throwable) {
            emit(Resource.Loading(false))
            emit(Resource.Error(errorMessage = e.message ?: ""))
        }
    }
}