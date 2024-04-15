package com.example.job_seeker.presentation.screen.job

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.domain.usecase.job.GetJobUseCase
import com.example.job_seeker.presentation.event.job.JobEvent
import com.example.job_seeker.presentation.mapper.jobs.toPresentation
import com.example.job_seeker.presentation.state.job.JobState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JobViewModel @Inject constructor(
    private val getJobUseCase: GetJobUseCase
) : ViewModel() {

    private val _jobState = MutableStateFlow(JobState())
    val jobState get() = _jobState.asStateFlow()

    fun onEvent(event: JobEvent) = with(event) {
        when (this) {
            is JobEvent.GetJob -> getJob(jobId = jobId)
            JobEvent.ResetErrorMessage -> updateErrorMessage()
        }
    }

    private fun getJob(jobId: String) {
        viewModelScope.launch {
            getJobUseCase(jobId = jobId).collect {
                when (it) {
                    is Resource.Loading -> _jobState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Success -> _jobState.update { currentState ->
                        currentState.copy(data = it.data.toPresentation())
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
        }
    }

    private fun updateErrorMessage(message: String? = null) {
        _jobState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }
}