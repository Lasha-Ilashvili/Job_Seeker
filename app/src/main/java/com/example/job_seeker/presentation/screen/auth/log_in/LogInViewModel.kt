package com.example.job_seeker.presentation.screen.auth.log_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job_seeker.R
import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.domain.usecase.auth.LogInUseCase
import com.example.job_seeker.domain.usecase.validator.auth.EmailValidatorUseCase
import com.example.job_seeker.domain.usecase.validator.auth.FieldsAreNotBlankUseCase
import com.example.job_seeker.domain.usecase.validator.auth.PasswordValidatorUseCase
import com.example.job_seeker.presentation.event.auth.log_in.LogInEvent
import com.example.job_seeker.presentation.state.auth.log_in.LogInState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val emailValidator: EmailValidatorUseCase,
    private val passwordValidator: PasswordValidatorUseCase,
    private val fieldsAreNotBlank: FieldsAreNotBlankUseCase,
    private val logInUseCase: LogInUseCase,
) : ViewModel() {

    private val _logInState = MutableStateFlow(LogInState())
    val logInState = _logInState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent get() = _uiEvent

    fun onEvent(event: LogInEvent) = with(event) {
        when (this) {
            is LogInEvent.LogIn -> validateForms(email = email, password = password)
            is LogInEvent.SetButtonState -> setButtonState(fields = fields)
            is LogInEvent.ResetErrorMessage -> updateErrorMessage(message = null)
        }
    }

    private fun validateForms(email: TextInputLayout, password: TextInputLayout) {
        val emailInput = email.editText?.text.toString()
        val passwordInput = password.editText?.text.toString()

        val isEmailValid = emailValidator(email = emailInput)
        val isPasswordValid = passwordValidator(password = passwordInput)

        updateErrorTextInputLayout(email, isEmailValid)
        updateErrorTextInputLayout(password, isPasswordValid.first, isPasswordValid.second)

        if (isEmailValid && isPasswordValid.first) {
            logIn(email = emailInput, password = passwordInput)
        }
    }

    private fun updateErrorTextInputLayout(
        errorTextInputLayout: TextInputLayout,
        isFieldValid: Boolean,
        inputErrorMessage: Int = R.string.invalid_input
    ) {
        _logInState.update { currentState ->
            currentState.copy(
                errorTextInputLayout = errorTextInputLayout,
                isErrorEnabled = !isFieldValid,
                inputErrorMessage = inputErrorMessage
            )
        }
    }

    private fun logIn(email: String, password: String) {
        viewModelScope.launch {
            logInUseCase(email = email, password = password).collect {
                when (it) {
                    is Resource.Success -> _uiEvent.emit(LoginUiEvent.NavigateToJobs)

                    is Resource.Error -> updateErrorMessage(it.errorMessage)

                    is Resource.Loading -> _logInState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }
                }
            }
        }
    }

    private fun setButtonState(fields: List<TextInputLayout>) {
        _logInState.update { currentState ->
            currentState.copy(
                isButtonEnabled = fieldsAreNotBlank(fields)
            )
        }
    }

    private fun updateErrorMessage(message: String?) {
        _logInState.update { currentState ->
            currentState.copy(
                errorMessage = message
            )
        }
    }

    sealed interface LoginUiEvent {
        data object NavigateToJobs : LoginUiEvent
    }
}