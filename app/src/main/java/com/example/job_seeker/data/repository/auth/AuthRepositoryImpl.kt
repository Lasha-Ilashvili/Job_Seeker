package com.example.job_seeker.data.repository.auth

import com.example.job_seeker.data.common.HandleResponse
import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.data.data_source.auth.AuthDataSource
import com.example.job_seeker.data.mapper.base.asResource
import com.example.job_seeker.domain.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val handleResponse: HandleResponse
) : AuthRepository {

    override fun logIn(email: String, password: String): Flow<Resource<Unit>> {
        return handleResponse.safeFireBaseCall {
            authDataSource.logIn(email, password)
        }.asResource {}
    }

    override fun signUp(email: String, password: String): Flow<Resource<Unit>> {
        return handleResponse.safeFireBaseCall {
            authDataSource.signUp(email, password)
        }.asResource {}
    }
}