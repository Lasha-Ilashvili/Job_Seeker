package com.example.job_seeker.presentation.screen.auth.sign_up

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.job_seeker.databinding.FragmentSignUpBinding
import com.example.job_seeker.presentation.base.BaseFragment
import com.example.job_seeker.presentation.event.auth.sign_up.SignUpEvent
import com.example.job_seeker.presentation.extension.hideKeyboard
import com.example.job_seeker.presentation.extension.showSnackBar
import com.example.job_seeker.presentation.state.auth.sign_up.SignUpState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val viewModel: SignUpViewModel by viewModels()

    override fun setListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            addTextListeners(listOf(etEmail, etPassword, etRepeatPassword))

            btnNext.setOnClickListener {
                it.hideKeyboard()

                signUp()
            }
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signUpState.collect {
                    handleState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(event = it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    private fun addTextListeners(fields: List<TextInputLayout>) {
        fields.forEach { textInputLayout ->
            textInputLayout.editText?.doAfterTextChanged {
                viewModel.onEvent(SignUpEvent.SetButtonState(fields))

                if (textInputLayout == binding.etPassword) {
                    viewModel.onEvent(SignUpEvent.SetPasswordStrengthState(it.toString()))
                }
            }
        }
    }

    private fun signUp() = with(binding) {
        viewModel.onEvent(
            SignUpEvent.SignUp(
                email = etEmail,
                password = etPassword,
                matchingPassword = etRepeatPassword
            )
        )
    }

    private fun handleState(signUpState: SignUpState) = with(signUpState) {
        binding.progressBar.root.isVisible = isLoading

        errorMessage?.let {
            binding.root.showSnackBar(it)
            viewModel.onEvent(SignUpEvent.ResetErrorMessage)
        }

        binding.btnNext.isEnabled = isButtonEnabled

        errorTextInputLayout?.let {
            it.error = getString(inputErrorMessage)
            it.isErrorEnabled = isErrorEnabled
        }

        passwordStrength?.let {
            binding.etPassword.helperText = getString(it.first)
            binding.etPassword.setHelperTextColor(
                ContextCompat.getColorStateList(requireContext(), it.second)
            )
        }
    }

    private fun handleNavigationEvents(event: SignUpViewModel.SignUpUiEvent) = with(event) {
        when (this) {
            is SignUpViewModel.SignUpUiEvent.NavigateBackToLogIn -> {
                val resultBundle = Bundle().apply {
                    putString("email", email)
                    putString("password", password)
                }

                parentFragmentManager.setFragmentResult("requestKey", resultBundle)

                findNavController().navigate(
                    SignUpFragmentDirections.actionSignUpFragmentToLogInFragment()
                )
            }
        }
    }
}