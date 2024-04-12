package com.example.job_seeker.presentation.state.auth.sign_up

import com.example.job_seeker.R
import com.google.android.material.textfield.TextInputLayout


data class SignUpState(
    val isLoading: Boolean = false,
    val errorTextInputLayout: TextInputLayout? = null,
    val isErrorEnabled: Boolean = false,
    val isButtonEnabled: Boolean = false,
    val passwordStrength: Pair<Int, Int>? = null,
    val errorMessage: String? = null,
    val inputErrorMessage: Int = R.string.invalid_input
)