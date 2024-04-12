package com.example.job_seeker.domain.usecase.auth

import com.example.job_seeker.domain.repository.auth.AuthRepository
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        authRepository.logIn(email, password)
}