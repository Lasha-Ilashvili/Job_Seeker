package com.example.job_seeker.presentation.state.auth.log_in

import com.example.job_seeker.R
import com.google.android.material.textfield.TextInputLayout

data class LogInState(
    val isLoading: Boolean = false,
    val errorTextInputLayout: TextInputLayout? = null,
    val token: String? = null,
    val errorMessage: String? = null,
    val isErrorEnabled: Boolean = false,
    val isButtonEnabled: Boolean = false,
    val inputErrorMessage: Int = R.string.invalid_input
)