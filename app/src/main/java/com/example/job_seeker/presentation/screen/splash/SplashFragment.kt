package com.example.job_seeker.presentation.screen.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.job_seeker.databinding.FragmentSplashBinding
import com.example.job_seeker.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    println("Inside collect")
                    handleNavigationEvents(event = it)
                }
            }
        }
    }

    private fun handleNavigationEvents(event: SplashViewModel.SplashUiEvent) {
        when (event) {
            is SplashViewModel.SplashUiEvent.NavigateToJobs ->
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToBottomNavFragment()
                )

            is SplashViewModel.SplashUiEvent.NavigateToHome ->
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                )
        }
    }
}