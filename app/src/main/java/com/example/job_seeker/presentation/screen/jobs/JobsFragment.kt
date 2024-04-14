package com.example.job_seeker.presentation.screen.jobs

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
//        viewModel.onEvent(JobsEvent.GetUserJobs)
        binding.root.setOnClickListener {
//            viewModel.onEvent(JobsEvent.DeleteUserJob(job.userUid, job.id))
//            viewModel.onEvent(JobsEvent.AddUserJob(job))
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.jobsState.collectLatest {
                    handleState(it)
                }
            }
        }
        adapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Error -> {
                    (loadState.refresh as LoadState.Error).error.message?.let { message ->
                        binding.root.showSnackBar(message = message)
                    }
                }

                else -> {
//                    println(loadState.refresh is LoadState.Loading)
//                    binding.progressBar.root.isVisible = isLoading
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    private fun setRecycler() {
        adapter = JobsListAdapter()
        binding.rvJobs.adapter = adapter
    }

    private fun getJobs() {
        viewModel.onEvent(JobsEvent.GetJobs)
    }

    private suspend fun handleState(jobsState: JobsState) = with(jobsState) {
        jobs?.let {
            adapter.submitData(it)
        }
    }
}