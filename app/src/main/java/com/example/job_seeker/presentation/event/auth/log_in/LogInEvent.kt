package com.example.job_seeker.presentation.event.auth.log_in

import com.google.android.material.textfield.TextInputLayout

sealed class LogInEvent {
    data class LogIn(val email: TextInputLayout, val password: TextInputLayout) : LogInEvent()

    data class SetButtonState(val fields: List<TextInputLayout>) : LogInEvent()

    data object ResetErrorMessage : LogInEvent()
}