package com.example.job_seeker.presentation.screen.auth.log_in

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.job_seeker.databinding.FragmentLogInBinding
import com.example.job_seeker.presentation.base.BaseFragment
import com.example.job_seeker.presentation.event.auth.log_in.LogInEvent
import com.example.job_seeker.presentation.extension.hideKeyboard
import com.example.job_seeker.presentation.extension.showSnackBar
import com.example.job_seeker.presentation.state.auth.log_in.LogInState
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInViewModel by viewModels()

    override fun setUp() {
        setFragmentResultListener("requestKey") { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")

            with(binding) {
                etEmailInput.setText(email)
                etPasswordInput.setText(password)
            }
        }
    }

    override fun setListeners() {
        with(binding) {
            btnBackArrow.setOnClickListener {
                findNavController().popBackStack()
            }

            btnLogIn.setOnClickListener {
                it.hideKeyboard()

                logInUser()
            }

            addTextListeners(listOf(etEmail, etPassword))
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleUiState()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.logInState.collect {
                    handleState(it)
                }
            }
        }
    }

    /* IMPLEMENTATION DETAILS */

    private fun logInUser() {
        viewModel.onEvent(
            LogInEvent.LogIn(
                email = binding.etEmail,
                password = binding.etPassword
            )
        )
    }

    private fun addTextListeners(fields: List<TextInputLayout>) {
        fields.forEach { textInputLayout ->
            textInputLayout.editText?.doAfterTextChanged {
                viewModel.onEvent(LogInEvent.SetButtonState(fields))
            }
        }
    }

    private fun handleState(state: LogInState) = with(state) {
        binding.progressBar.root.isVisible = isLoading

        binding.btnLogIn.isEnabled = isButtonEnabled

        errorMessage?.let {
            binding.root.showSnackBar(it)
            viewModel.onEvent(LogInEvent.ResetErrorMessage)
        }

        errorTextInputLayout?.let {
            it.error = getString(inputErrorMessage)
            it.isErrorEnabled = isErrorEnabled
        }
    }

    private fun handleUiState() {
        findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToBottomNavFragment())
    }
}