package com.example.job_seeker.presentation.screen.job

import android.content.Intent
import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.job_seeker.R
import com.example.job_seeker.databinding.FragmentJobBinding
import com.example.job_seeker.presentation.base.BaseFragment
import com.example.job_seeker.presentation.event.job.JobEvent
import com.example.job_seeker.presentation.extension.showAlertDialog
import com.example.job_seeker.presentation.extension.showSnackBar
import com.example.job_seeker.presentation.model.jobs.Job
import com.example.job_seeker.presentation.state.job.JobState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class JobFragment : BaseFragment<FragmentJobBinding>(FragmentJobBinding::inflate) {

    private val viewModel: JobViewModel by viewModels()
    private val args: JobFragmentArgs by navArgs()
    private lateinit var job: Job
    private var redirectedToWebPage = false

    override fun setUp() {
        viewModel.onEvent(JobEvent.GetJobApplicants(jobId = args.jobId))
        viewModel.onEvent(JobEvent.GetJob(jobId = args.jobId))
        binding.root.setOnClickListener {
            redirectedToWebPage = true
            openWebPage()
        }
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

    /* IMPLEMENTATION DETAILS */

    private fun handleState(jobState: JobState) = with(jobState) {
        binding.progressBar.root.isVisible = isLoading

        errorMessage?.let {
            binding.root.showSnackBar(it)
            viewModel.onEvent(JobEvent.ResetErrorMessage)
        }

        addUserJobMessage?.let {
            binding.root.showSnackBar(getString(it))
            viewModel.onEvent(JobEvent.ResetAddUserJobMessage)
        }

        userJobExists?.let {
            redirectedToWebPage = false
            if (!it) setUpDialog()

            viewModel.onEvent(JobEvent.ResetUserJobState)
        }

        data?.let {
            job = it
//            binding.tvTitle.text = it.title

            if (redirectedToWebPage) viewModel.onEvent(JobEvent.CheckUserJob(jobId = job.id))
        }

        jobApplicants?.let {
            binding.tvTitle.text = it.toString()
        }
    }

    private fun openWebPage() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(job.redirectUrl)
        )
        startActivity(intent)
    }

    private fun setUpDialog() {
        requireContext().showAlertDialog(
            title = getString(R.string.apply_question),
            message = getString(R.string.apply_question_explanation),
            positiveButtonText = getString(R.string.yes),
            negativeButtonText = getString(R.string.no),
            positiveButtonClickAction = {
                viewModel.onEvent(JobEvent.AddUserJob(job = job))
                viewModel.onEvent(JobEvent.UpdateJobApplicants(jobId = args.jobId))
            }
        )
    }
}