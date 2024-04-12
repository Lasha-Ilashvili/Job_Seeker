package com.example.job_seeker.presentation.screen.user_panel_bottom_sheet

import androidx.fragment.app.viewModels
import com.example.job_seeker.R
import com.example.job_seeker.databinding.FragmentUserPanelBottomSheetBinding
import com.example.job_seeker.presentation.base.BaseBottomSheet
import com.example.job_seeker.presentation.event.user_panel_bottom_sheet.UserPanelBottomSheetEvent
import com.example.job_seeker.presentation.extension.restartApp
import com.example.job_seeker.presentation.extension.showAlertDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserPanelBottomSheet :
    BaseBottomSheet<FragmentUserPanelBottomSheetBinding>(FragmentUserPanelBottomSheetBinding::inflate) {

    private val viewModel: UserPanelBottomSheetViewModel by viewModels()

    override fun setListeners() {
        binding.root.setOnClickListener {
            setUpDialog()
        }
    }

    /* IMPLEMENTATION DETAILS */

    private fun setUpDialog() {
        requireContext().showAlertDialog(
            title = getString(R.string.log_out),
            message = getString(R.string.do_you_want_to_log_out),
            positiveButtonText = getString(R.string.yes),
            negativeButtonText = getString(R.string.no),
            positiveButtonClickAction = {
                viewModel.onEvent(UserPanelBottomSheetEvent.LogOut)
                restartApp(requireActivity())
            }
        )
    }
}