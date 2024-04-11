package com.example.job_seeker.domain.model.user_jobs


data class GetUserJob(
    val id: String,
    val documentId: String?,
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
    val latitude: Double?,
    val longitude: Double?
)