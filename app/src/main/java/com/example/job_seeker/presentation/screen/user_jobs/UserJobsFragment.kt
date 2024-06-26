package com.example.job_seeker.presentation.screen.user_jobs

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.job_seeker.databinding.FragmentUserJobsBinding
import com.example.job_seeker.presentation.base.BaseFragment
import com.example.job_seeker.presentation.event.user_jobs.UserJobsEvent
import com.example.job_seeker.presentation.extension.showSnackBar
import com.example.job_seeker.presentation.model.user_jobs.UserJob
import com.example.job_seeker.presentation.screen.user_jobs.adapter.UserJobsListAdapter
import com.example.job_seeker.presentation.state.user_jobs.UserJobsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UserJobsFragment : BaseFragment<FragmentUserJobsBinding>(FragmentUserJobsBinding::inflate) {

    private val viewModel: UserJobsViewModel by viewModels()
    private lateinit var userJob: UserJob

    override fun setUp() {
        viewModel.onEvent(UserJobsEvent.GetUserJobs)
    }

    override fun setListeners() {
        setRefreshListener()
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(event = it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userJobsState.collect {
                    handleState(it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    private fun setRefreshListener() = with(binding.userJobsSwipeRefresh) {
        setOnRefreshListener {
            isRefreshing = false

            viewModel.onEvent(UserJobsEvent.GetUserJobs)
        }
    }

    private fun handleState(userJobsState: UserJobsState) = with(userJobsState) {
        binding.progressBar.root.isVisible = isLoading

        errorMessage?.let {
            binding.root.showSnackBar(it)
            viewModel.onEvent(UserJobsEvent.ResetErrorMessage)
        }

        data?.let {
            binding.rvUserJobs.adapter = UserJobsListAdapter().apply {
                onClick = ::openUserJob
                onDeleteClick = ::deleteUserJob
                submitList(it)
            }

            if (it.isNotEmpty())
                userJob = it.first()
        }
    }

    private fun openUserJob(jobId: String) {
        viewModel.onEvent(UserJobsEvent.OpenUserJob(jobId = jobId))
    }

    private fun deleteUserJob(jobId: String) {
        viewModel.onEvent(UserJobsEvent.DeleteUserJob(jobId = jobId))
    }

    private fun handleNavigationEvents(event: UserJobsViewModel.UserJobsUiEvent) = with(event) {
        when (this) {
            is UserJobsViewModel.UserJobsUiEvent.NavigateToUserJob -> findNavController().navigate(
                UserJobsFragmentDirections.actionUserJobsFragmentToJobFragment(jobId = jobId)
            )
        }
    }
}