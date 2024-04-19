package com.example.job_seeker.presentation.screen.jobs

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.job_seeker.databinding.FragmentJobsBinding
import com.example.job_seeker.presentation.base.BaseFragment
import com.example.job_seeker.presentation.event.jobs.JobsEvent
import com.example.job_seeker.presentation.extension.showSnackBar
import com.example.job_seeker.presentation.screen.jobs.adapter.JobsListAdapter
import com.example.job_seeker.presentation.state.jobs.JobsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class JobsFragment : BaseFragment<FragmentJobsBinding>(FragmentJobsBinding::inflate) {

    private val viewModel: JobsViewModel by viewModels()
    private lateinit var adapter: JobsListAdapter

    override fun setUp() {
        setRecycler()
        getJobs()
    }

    override fun setListeners() {
        setRefreshListener()
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.jobsState.collectLatest {
                    handleState(it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    private fun setRecycler() {
        adapter = JobsListAdapter().apply {
            onClick = ::navigateToJob
        }
        binding.rvJobs.adapter = adapter
    }

    private fun getJobs() {
        viewModel.onEvent(JobsEvent.GetJobs)
    }

    private fun setRefreshListener() = with(binding.jobsSwipeRefresh) {
        setOnRefreshListener {
            isRefreshing = false

            getJobs()
        }
    }

    private suspend fun handleState(jobsState: JobsState) {
        handleLoadState()

        jobsState.jobs?.let {
            adapter.submitData(it)
        }
    }

    private fun handleLoadState() = with(binding) {
        adapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Error -> {
                    (loadState.refresh as LoadState.Error).error.message?.let { message ->
                        root.showSnackBar(message = message)
                    }
                }

                else -> {
                    progressBar.root.isVisible = loadState.refresh is LoadState.Loading
                }
            }
        }
    }

    private fun navigateToJob(jobId: String) {
        findNavController().navigate(
            JobsFragmentDirections.actionJobsFragmentToJobFragment(
                jobId = jobId
            )
        )
    }
}