package com.example.job_seeker.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job_seeker.domain.usecase.auth.FireBaseAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val fireBaseAuthUseCase: FireBaseAuthUseCase
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<SplashUiEvent>()
    val uiEvent get() = _uiEvent.asSharedFlow()

    init {
        readSession()
    }

    private fun readSession() {
        viewModelScope.launch {
            fireBaseAuthUseCase().collect { isLoggedIn ->
                _uiEvent.emit(
                    if (isLoggedIn)
                        SplashUiEvent.NavigateToJobs
                    else
                        SplashUiEvent.NavigateToHome
                )
            }
        }
    }

    sealed interface SplashUiEvent {
        data object NavigateToJobs : SplashUiEvent
        data object NavigateToHome : SplashUiEvent
    }
}