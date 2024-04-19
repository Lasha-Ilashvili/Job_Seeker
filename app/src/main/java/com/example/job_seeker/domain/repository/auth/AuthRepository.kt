package com.example.job_seeker.domain.repository.auth

import com.example.job_seeker.data.common.Resource
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    fun logIn(email: String, password: String): Flow<Resource<Unit>>
    fun signUp(email: String, password: String): Flow<Resource<Unit>>
}