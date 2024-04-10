package com.example.job_seeker.presentation.mapper.jobs

import com.example.job_seeker.domain.model.jobs.GetJob
import com.example.job_seeker.presentation.extension.toPresentation
import com.example.job_seeker.presentation.model.jobs.Job


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
        "$formattedMin/yr"
    } else {
        "$formattedMin - $formattedMax/yr"
    }
}

private fun formatSalary(salary: Double): String {
    return if (salary >= 1000) {
        "$${salary / 1000}K"
    } else {
        "$${salary}"
    }
}