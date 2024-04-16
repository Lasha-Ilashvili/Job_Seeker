package com.example.job_seeker.presentation.screen.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.job_seeker.domain.usecase.jobs.GetJobsUseCase
import com.example.job_seeker.presentation.event.jobs.JobsEvent
import com.example.job_seeker.presentation.mapper.jobs.toPresentation
import com.example.job_seeker.presentation.state.jobs.JobsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
            getJobsUseCase().cachedIn(viewModelScope).collectLatest { pagingData ->
                _jobsState.update { currentState ->
                    currentState.copy(jobs = pagingData.map { it.toPresentation() })
                }
            }
        }
    }
}