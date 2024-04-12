package com.example.job_seeker.domain.usecase.validator.auth


import javax.inject.Inject

class EmailValidatorUseCase @Inject constructor() {

    private val emailRegex = Regex(
        "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    )

    operator fun invoke(email: String) = email.matches(emailRegex)

}