package com.example.job_seeker.presentation.screen.user_jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.domain.usecase.auth.FireBaseUserUidUseCase
import com.example.job_seeker.domain.usecase.user_jobs.DeleteUserJobUseCase
import com.example.job_seeker.domain.usecase.user_jobs.GetUserJobsUseCase
import com.example.job_seeker.presentation.event.user_jobs.UserJobsEvent
import com.example.job_seeker.presentation.mapper.user_job.toPresentation
import com.example.job_seeker.presentation.state.user_jobs.UserJobsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserJobsViewModel @Inject constructor(
    private val fireBaseUserUidUseCase: FireBaseUserUidUseCase,
    private val getUserJobsUseCase: GetUserJobsUseCase,
    private val deleteUserJobUseCase: DeleteUserJobUseCase
) : ViewModel() {

    private val _userJobsState = MutableStateFlow(UserJobsState())
    val userJobsState get() = _userJobsState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UserJobsUiEvent>()
    val uiEvent get() = _uiEvent

    fun onEvent(event: UserJobsEvent) = with(event) {
        when (this) {
            is UserJobsEvent.OpenUserJob -> openUserJob(jobId = jobId)
            is UserJobsEvent.DeleteUserJob -> deleteUserJob(jobId = jobId)
            UserJobsEvent.GetUserJobs -> getUserJobs()
            UserJobsEvent.ResetErrorMessage -> updateErrorMessage()
        }
    }

    private fun getUserJobs() {
        viewModelScope.launch {
            getUserJobsUseCase(userUid = fireBaseUserUidUseCase()).collect {
                when (it) {
                    is Resource.Success -> _userJobsState.update { currentState ->
                        currentState.copy(data = it.data.map { getUserJob ->
                            getUserJob.toPresentation()
                        })
                    }

                    is Resource.Loading -> _userJobsState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
        }
    }

    private fun openUserJob(jobId: String){
        viewModelScope.launch {
            _uiEvent.emit(UserJobsUiEvent.NavigateToUserJob(jobId = jobId))
        }
    }

    private fun deleteUserJob(jobId: String) {
        viewModelScope.launch {
            deleteUserJobUseCase(userUid = fireBaseUserUidUseCase(), jobId = jobId).collect {
                when (it) {
                    is Resource.Success -> getUserJobs()

                    is Resource.Loading -> _userJobsState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
        }
    }

    private fun updateErrorMessage(message: String? = null) {
        _userJobsState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }

    sealed interface UserJobsUiEvent {
        data class NavigateToUserJob(val jobId:String) : UserJobsUiEvent
    }
}