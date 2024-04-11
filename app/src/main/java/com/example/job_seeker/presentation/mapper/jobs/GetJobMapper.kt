package com.example.job_seeker.presentation.mapper.jobs

import com.example.job_seeker.domain.model.jobs.GetJob
import com.example.job_seeker.presentation.extension.toPresentation
import com.example.job_seeker.presentation.model.jobs.Job
import kotlin.math.roundToInt


fun GetJob.toPresentation() = Job(
    id = id,
    title = title,
    company = company,
    date = created.toPresentation(),
    category = category,
    contractType = contractType,
    contractTime = contractTime,
    salary = formatSalary(salaryMin, salaryMax),
    description = description,
    location = location,
    redirectUrl = redirectUrl,
    latitude = latitude,
    longitude = longitude
)

private fun formatSalary(salaryMin: Double, salaryMax: Double): String {
    val formattedMin = formatSalary(salaryMin)
    val formattedMax = formatSalary(salaryMax)

    return if (salaryMin == salaryMax) {
        formattedMin
    } else {
        "$formattedMin - $formattedMax"
    }
}

private fun formatSalary(salary: Double): String = "$${(salary / 1000.0).roundToInt()}K/yr"