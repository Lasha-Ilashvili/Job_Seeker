package com.example.job_seeker.presentation.screen.user_panel_bottom_sheet

import androidx.lifecycle.ViewModel
import com.example.job_seeker.domain.usecase.auth.FireBaseAuthLogOutUseCase
import com.example.job_seeker.presentation.event.user_panel_bottom_sheet.UserPanelBottomSheetEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserPanelBottomSheetViewModel @Inject constructor(
    private val fireBaseAuthLogOutUseCase: FireBaseAuthLogOutUseCase
) : ViewModel() {

    fun onEvent(event: UserPanelBottomSheetEvent) {
        when (event) {
            is UserPanelBottomSheetEvent.LogOut -> logOut()
        }
    }

    private fun logOut() {
        fireBaseAuthLogOutUseCase()
    }
}