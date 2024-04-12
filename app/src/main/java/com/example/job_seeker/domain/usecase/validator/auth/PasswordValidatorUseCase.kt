package com.example.job_seeker.domain.usecase.validator.auth

import com.example.job_seeker.R
import javax.inject.Inject


private const val EXPECTED_LENGTH = 8

class PasswordValidatorUseCase @Inject constructor() {
    operator fun invoke(password: String): Pair<Boolean, Int> =
        (password.length >= EXPECTED_LENGTH) to R.string.password_error
}