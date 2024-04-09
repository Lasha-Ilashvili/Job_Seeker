package com.example.job_seeker.presentation.screen.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.domain.usecase.jobs.GetJobsUseCase
import com.example.job_seeker.presentation.event.jobs.JobsEvent
import com.example.job_seeker.presentation.state.jobs.JobsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JobsViewModel @Inject constructor(
    private val getJobsUseCase: GetJobsUseCase
) : ViewModel() {

    private val _jobsState = MutableStateFlow(JobsState())
    val jobsState get() = _jobsState.asStateFlow()

    fun onEvent(event: JobsEvent) = with(event) {
        when (this) {
            JobsEvent.GetJobs -> getJobs()
        }
    }

    private fun getJobs() {
        viewModelScope.launch {
            getJobsUseCase()
//                .cachedIn(viewModelScope)
//                .collectLatest {
                .collect {
                    if (it is Resource.Success)
                        _jobsState.update { currentState ->
                            currentState.copy(data = it.data)
                        }
                }
        }
    }
}