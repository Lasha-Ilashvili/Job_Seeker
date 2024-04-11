package com.example.job_seeker.presentation.screen.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.domain.usecase.jobs.GetJobsUseCase
import com.example.job_seeker.domain.usecase.user_jobs.AddUserJobUseCase
import com.example.job_seeker.domain.usecase.user_jobs.DeleteUserJobUseCase
import com.example.job_seeker.domain.usecase.user_jobs.GetUserJobsUseCase
import com.example.job_seeker.presentation.event.jobs.JobsEvent
import com.example.job_seeker.presentation.mapper.jobs.toPresentation
import com.example.job_seeker.presentation.mapper.user_job.toDomain
import com.example.job_seeker.presentation.mapper.user_job.toPresentation
import com.example.job_seeker.presentation.model.jobs.Job
import com.example.job_seeker.presentation.model.user_jobs.UserJob
import com.example.job_seeker.presentation.state.jobs.JobsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JobsViewModel @Inject constructor(
    private val getJobsUseCase: GetJobsUseCase,
    private val addUserJobUseCase: AddUserJobUseCase,
    private val getUserJobsUseCase: GetUserJobsUseCase,
    private val deleteUserJobUseCase: DeleteUserJobUseCase
) : ViewModel() {

    private val _jobsState = MutableStateFlow(JobsState())
    val jobsState get() = _jobsState.asStateFlow()

    fun onEvent(event: JobsEvent) = with(event) {
        when (this) {
            is JobsEvent.AddUserJob -> addUserJob(job = job)
            is JobsEvent.DeleteUserJob -> deleteUserJob(documentId = documentId)
            JobsEvent.GetJobs -> getJobs()
            JobsEvent.GetUserJobs -> getUserJobs()
            JobsEvent.ResetErrorMessage -> updateErrorMessage()
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
                            currentState.copy(jobs = it.data.map { getJob ->
                                getJob.toPresentation()
                            })
                        }
                    else if (it is Resource.Error)
                        updateErrorMessage(it.errorMessage)
                }
        }
    }

    private fun addUserJob(job: Job) {
        viewModelScope.launch {
            addUserJobUseCase(userJob = getUserJob(job).toDomain()).collect {
                when (it) {
                    is Resource.Success -> {}

                    is Resource.Loading -> _jobsState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
        }
    }

    private fun getUserJob(job: Job): UserJob = with(job) {
        UserJob(
            id = id,
            title = title,
            company = company,
            date = date,
            category = category,
            contractType = contractType,
            contractTime = contractTime,
            salary = salary,
            description = description,
            location = location,
            redirectUrl = redirectUrl,
            latitude = latitude,
            longitude = longitude
        )
    }

    private fun getUserJobs() {
        viewModelScope.launch {
            getUserJobsUseCase().collect {
                when (it) {
                    is Resource.Success -> _jobsState.update { currentState ->
                        currentState.copy(userJobs = it.data.map { getUserJob ->
                            getUserJob.toPresentation()
                        })
                    }

                    is Resource.Loading -> _jobsState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
        }
    }

    private fun deleteUserJob(documentId: String?) {
        viewModelScope.launch {
            deleteUserJobUseCase(documentId = documentId).collect {
                when (it) {
                    is Resource.Success -> {}

                    is Resource.Loading -> _jobsState.update { currentState ->
                        currentState.copy(isLoading = it.loading)
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
        }
    }

    private fun updateErrorMessage(message: String? = null) {
        _jobsState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }
}