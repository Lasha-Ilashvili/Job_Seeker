package com.example.job_seeker.data.model.jobs

import com.squareup.moshi.Json


data class JobDto(
    @Json(name = "results")
    val jobItems: List<JobItem>
) {
    data class JobItem(
        val id: String,
        @Json(name = "salary_max")
        val salaryMax: Double?,
        @Json(name = "salary_min")
        val salaryMin: Double?,
        val created: String,
        val category: Category,
        @Json(name = "contract_type")
        val contractType: String?,
        @Json(name = "contract_time")
        val contractTime: String?,
        @Json(name = "redirect_url")
        val redirectUrl: String,
        val company: Company,
        val location: Location,
        val title: String,
        val description: String,
        val latitude: Double?,
        val longitude: Double?
    ) {
        data class Category(val category: String?)

        data class Company(@Json(name = "display_name") val company: String)

        data class Location(@Json(name = "display_name") val location: String)
    }
}