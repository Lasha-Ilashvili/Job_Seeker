package com.example.job_seeker.presentation.screen.auth.home

import androidx.navigation.fragment.findNavController
import com.example.job_seeker.databinding.FragmentHomeBinding
import com.example.job_seeker.presentation.base.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun setListeners() {
        with(binding) {
            btnLogIn.setOnClickListener {
                navigateToLogInFragment()
            }

            btnSignUp.setOnClickListener {
                navigateToSignUpFragment()
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    private fun navigateToLogInFragment() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLogInFragment())
    }

    private fun navigateToSignUpFragment() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSignUpFragment())
    }
}