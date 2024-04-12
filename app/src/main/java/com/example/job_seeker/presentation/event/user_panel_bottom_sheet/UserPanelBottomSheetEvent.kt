package com.example.job_seeker.presentation.event.user_panel_bottom_sheet


sealed class UserPanelBottomSheetEvent {
    data object LogOut : UserPanelBottomSheetEvent()
}