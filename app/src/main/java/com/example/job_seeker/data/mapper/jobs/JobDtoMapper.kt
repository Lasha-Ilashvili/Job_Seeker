package com.example.job_seeker.data.mapper.jobs

import com.example.job_seeker.data.model.jobs.JobDto
import com.example.job_seeker.domain.model.jobs.GetJob


fun JobDto.toDomain() = jobItems.map {
    it.toDomain()
}

private fun JobDto.JobItem.toDomain() = GetJob(
    id = id,
    salaryMax = salaryMax,
    salaryMin = salaryMin,
    created = created,
    category = category.category,
    contractType = contractType,
    contractTime = contractTime,
    redirectUrl = redirectUrl,
    company = company.company,
    location = location.location,
    title = title,
    description = description,
    latitude = latitude,
    longitude = longitude
)