package com.example.job_seeker.presentation.event.auth.sign_up

import com.google.android.material.textfield.TextInputLayout


sealed class SignUpEvent {
    data object ResetErrorMessage : SignUpEvent()
    data class SetButtonState(val fields: List<TextInputLayout>) : SignUpEvent()
    data class SignUp(
        val email: TextInputLayout,
        val password: TextInputLayout,
        val matchingPassword: TextInputLayout
    ) : SignUpEvent()
    data class SetPasswordStrengthState(val password: String) : SignUpEvent()
}