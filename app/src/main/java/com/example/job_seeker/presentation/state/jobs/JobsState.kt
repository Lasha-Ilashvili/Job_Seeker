package com.example.job_seeker.presentation.state.jobs

import okhttp3.ResponseBody


data class JobsState(
    val data: ResponseBody? = null
)