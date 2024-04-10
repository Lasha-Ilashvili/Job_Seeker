package com.example.job_seeker.domain.model.jobs


data class GetJob(
    val id: String,
    val title: String,
    val company: String,
    val created: String,
    val category: String?,
    val contractType: String?,
    val contractTime: String?,
    val salaryMax: Double,
    val salaryMin: Double,
    val description: String,
    val location: String,
    val redirectUrl: String,
    val latitude: Double?,
    val longitude: Double?
)