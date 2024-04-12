package com.example.job_seeker.domain.repository.auth

import com.example.job_seeker.data.common.Resource
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    suspend fun logIn(email: String, password: String): Flow<Resource<Unit>>
    suspend fun signUp(email: String, password: String): Flow<Resource<Unit>>
}