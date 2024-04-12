package com.example.job_seeker.presentation.screen.auth.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job_seeker.R
import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.domain.usecase.auth.SignUpUseCase
import com.example.job_seeker.domain.usecase.validator.auth.EmailValidatorUseCase
import com.example.job_seeker.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.job_seeker.domain.usecase.validator.auth.MatchingPasswordValidatorUseCase
import com.example.job_seeker.domain.usecase.validator.auth.PasswordStrengthMeterUseCase
import com.example.job_seeker.presentation.event.auth.sign_up.SignUpEvent
import com.example.job_seeker.presentation.state.auth.sign_up.SignUpState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase,
    private val emailValidator: EmailValidatorUseCase,
    private val matchingPasswordValidator: MatchingPasswordValidatorUseCase,
    private val passwordStrengthMeter: PasswordStrengthMeterUseCase
) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState get() = _signUpState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<SignUpUiEvent>()
    val uiEvent get() = _uiEvent

    fun onEvent(event: SignUpEvent) = with(event) {
        when (this) {
            is SignUpEvent.SetButtonState -> setButtonState(fields = fields)

            is SignUpEvent.SignUp -> validateFields(
                email = email,
                password = password,
                matchingPassword = matchingPassword
            )

            SignUpEvent.ResetErrorMessage -> updateErrorMessage()

            is SignUpEvent.SetPasswordStrengthState -> setPasswordStrengthState(password = password)
        }
    }

    private fun validateFields(
        email: TextInputLayout,
        password: TextInputLayout,
        matchingPassword: TextInputLayout
    ) {
        val emailInput = email.editText?.text.toString()
        val passwordInput = password.editText?.text.toString()
        val matchingPasswordInput = matchingPassword.editText?.text.toString()

        val isEmailValid = emailValidator(emailInput)
        val isPasswordValid = passwordStrengthMeter(passwordInput).first == R.string.password_strong
        val isMatchingPasswordValid =
            matchingPasswordValidator(passwordInput, matchingPasswordInput)

        val areFieldsValid =
            listOf(isEmailValid, isPasswordValid, isMatchingPasswordValid.first).all { it }

        validateField(isEmailValid, email)
        validateField(
            isMatchingPasswordValid.first, matchingPassword, isMatchingPasswordValid.second
        )

        if (!areFieldsValid) {
            return
        }

        signUp(email = emailInput, password = passwordInput)
    }

    private fun validateField(
        isFieldValid: Boolean,
        textInputLayout: TextInputLayout,
        inputErrorMessage: Int = R.string.invalid_input
    ) {
        updateErrorTextInputLayout(
            errorTextInputLayout = textInputLayout,
            isErrorEnabled = !isFieldValid,
            inputErrorMessage = inputErrorMessage
        )
    }

    private fun updateErrorTextInputLayout(
        errorTextInputLayout: TextInputLayout,
        isErrorEnabled: Boolean,
        inputErrorMessage: Int
    ) {
        _signUpState.update { currentState ->
            currentState.copy(
                errorTextInputLayout = errorTextInputLayout,
                isErrorEnabled = isErrorEnabled,
                inputErrorMessage = inputErrorMessage
            )
        }
    }

    private fun signUp(email: String, password: String) {
        viewModelScope.launch {
            signUpUseCase(email = email, password = password).collect {
                when (it) {
                    is Resource.Success -> {
                        _uiEvent.emit(
                            SignUpUiEvent.NavigateBackToLogIn(
                                email = email, password = password
                            )
                        )
                    }

                    is Resource.Loading -> _signUpState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
        }
    }

    private fun setButtonState(fields: List<TextInputLayout>) {
        _signUpState.update { currentState ->
            currentState.copy(isButtonEnabled = fieldsAreNotBlank(fields))
        }

    }

    private fun updateErrorMessage(message: String? = null) {
        _signUpState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }

    private fun setPasswordStrengthState(password: String) {
        _signUpState.update { currentState ->
            currentState.copy(passwordStrength = passwordStrengthMeter(password))
        }
    }

    sealed interface SignUpUiEvent {
        data class NavigateBackToLogIn(val email: String, val password: String) : SignUpUiEvent
    }
}