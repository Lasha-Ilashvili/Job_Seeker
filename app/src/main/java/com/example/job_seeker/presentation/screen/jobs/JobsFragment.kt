package com.example.job_seeker.presentation.screen.jobs

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.job_seeker.databinding.FragmentJobsBinding
import com.example.job_seeker.presentation.base.BaseFragment
import com.example.job_seeker.presentation.event.jobs.JobsEvent
import com.example.job_seeker.presentation.state.jobs.JobsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class JobsFragment : BaseFragment<FragmentJobsBinding>(FragmentJobsBinding::inflate) {

    private val viewModel: JobsViewModel by viewModels()

    override fun setUp() {
//        getJobs()
        viewModel.onEvent(JobsEvent.GetUserJobs)
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.jobsState.collect {
                    handleState(it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    private fun getJobs() {
        viewModel.onEvent(JobsEvent.GetJobs)
    }

    private fun handleState(jobsState: JobsState) = with(jobsState) {
        errorMessage?.let {
            println(it)
            viewModel.onEvent(JobsEvent.ResetErrorMessage)
        }

        jobs?.let {

        }

        userJobs?.let {

        }
    }
}