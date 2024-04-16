package com.example.job_seeker.presentation.model.user_jobs


data class UserJob(
    val id: String,
    val title: String,
    val company: String,
    val date: String,
    val category: String?,
    val contractType: String?,
    val contractTime: String?,
    val salary: String,
    val description: String,
    val location: String,
    val redirectUrl: String,
)