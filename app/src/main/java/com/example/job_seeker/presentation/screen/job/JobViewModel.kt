package com.example.job_seeker.presentation.screen.job

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job_seeker.R
import com.example.job_seeker.data.common.Resource
import com.example.job_seeker.domain.usecase.auth.FireBaseUserUidUseCase
import com.example.job_seeker.domain.usecase.job.GetJobUseCase
import com.example.job_seeker.domain.usecase.job_applicants.GetJobApplicantsUseCase
import com.example.job_seeker.domain.usecase.job_applicants.UpdateJobApplicantsUseCase
import com.example.job_seeker.domain.usecase.user_jobs.AddUserJobUseCase
import com.example.job_seeker.domain.usecase.user_jobs.UserJobExistsUseCase
import com.example.job_seeker.presentation.event.job.JobEvent
import com.example.job_seeker.presentation.mapper.jobs.toPresentation
import com.example.job_seeker.presentation.mapper.user_job.toDomain
import com.example.job_seeker.presentation.model.jobs.Job
import com.example.job_seeker.presentation.model.user_jobs.UserJob
import com.example.job_seeker.presentation.state.job.JobState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JobViewModel @Inject constructor(
    private val fireBaseUserUidUseCase: FireBaseUserUidUseCase,
    private val getJobUseCase: GetJobUseCase,
    private val userJobExists: UserJobExistsUseCase,
    private val addUserJobUseCase: AddUserJobUseCase,
    private val getJobApplicantsUseCase: GetJobApplicantsUseCase,
    private val updateJobApplicantsUseCase: UpdateJobApplicantsUseCase
) : ViewModel() {

    private val _jobState = MutableStateFlow(JobState())
    val jobState get() = _jobState.asStateFlow()

    fun onEvent(event: JobEvent) = with(event) {
        when (this) {
            is JobEvent.UpdateJobApplicants -> updateJobApplicants(jobId = jobId)
            is JobEvent.GetJobApplicants -> getJobApplicants(jobId = jobId)
            is JobEvent.GetJob -> getJob(jobId = jobId)
            is JobEvent.CheckUserJob -> checkUserJob(jobId = jobId)
            is JobEvent.AddUserJob -> addUserJob(job = job)
            JobEvent.ResetErrorMessage -> updateErrorMessage()
            JobEvent.ResetAddUserJobMessage -> updateAddUserJobMessage()
            JobEvent.ResetUserJobState -> updateUserJobState()
        }
    }

    private fun updateJobApplicants(jobId: String) {
        viewModelScope.launch {
            updateJobApplicantsUseCase(jobId = jobId, userUid = fireBaseUserUidUseCase())
        }
    }

    private fun getJobApplicants(jobId: String) {
        viewModelScope.launch {
            getJobApplicantsUseCase(jobId = jobId).collect {
                when (it) {
                    is Resource.Loading -> {}

                    is Resource.Success -> _jobState.update { currentState ->
                        currentState.copy(jobApplicants = it.data.toInt())
                    }

                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)
                }
            }
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

    private fun checkUserJob(jobId: String) {
        viewModelScope.launch {
            userJobExists(userUid = fireBaseUserUidUseCase(), jobId = jobId).collect {
                when (it) {
                    is Resource.Success -> updateUserJobState(if (it.data) null else false)

                    is Resource.Error -> updateUserJobState(false)

                    is Resource.Loading -> {}
                }
            }
        }
    }

    private fun addUserJob(job: Job) {
        viewModelScope.launch {
            addUserJobUseCase(
                userUid = fireBaseUserUidUseCase(),
                userJob = getUserJob(job).toDomain()
            ).collect {
                when (it) {
                    is Resource.Success -> updateAddUserJobMessage(R.string.add_user_job_state)

                    is Resource.Loading -> _jobState.update { currentState ->
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
            redirectUrl = redirectUrl
        )
    }

    private fun updateErrorMessage(message: String? = null) {
        _jobState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }

    private fun updateAddUserJobMessage(message: Int? = null) {
        _jobState.update { currentState ->
            currentState.copy(addUserJobMessage = message)
        }
    }

    private fun updateUserJobState(userJobExists: Boolean? = null) {
        _jobState.update { currentState ->
            currentState.copy(userJobExists = userJobExists)
        }
    }
}