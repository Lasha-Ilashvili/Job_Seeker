package com.example.job_seeker.domain.usecase.validator.auth

import com.example.job_seeker.R
import javax.inject.Inject



class MatchingPasswordValidatorUseCase @Inject constructor() {

    operator fun invoke(password: String, matchingPassword: String): Pair<Boolean, Int> =
        (password == matchingPassword) to R.string.matching_password_error
}