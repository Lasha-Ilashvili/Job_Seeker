package com.example.job_seeker.data.model.user_jobs


data class UserJobDto(
    val id: String = "",
    val userUid: String = "",
    val title: String = "",
    val company: String = "",
    val date: String = "",
    val category: String? = null,
    val contractType: String? = null,
    val contractTime: String? = null,
    val salary: String = "",
    val description: String = "",
    val location: String = "",
    val redirectUrl: String = "",
)