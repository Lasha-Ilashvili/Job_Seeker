package com.example.job_seeker.presentation.screen.job

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.job_seeker.databinding.FragmentJobBinding
import com.example.job_seeker.presentation.base.BaseFragment
import com.example.job_seeker.presentation.event.job.JobEvent
import com.example.job_seeker.presentation.extension.showSnackBar
import com.example.job_seeker.presentation.state.job.JobState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class JobFragment : BaseFragment<FragmentJobBinding>(FragmentJobBinding::inflate) {

    private val viewModel: JobViewModel by viewModels()

    override fun setUp() {
        viewModel.onEvent(JobEvent.GetJob(jobId = "4519524111"))
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.jobState.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(jobState: JobState) = with(jobState) {
//        binding.progressBar.root.isVisible = isLoading

        errorMessage?.let {
            binding.root.showSnackBar(errorMessage)
            viewModel.onEvent(JobEvent.ResetErrorMessage)
        }

        data?.let {
            println(it)
        }
    }
}