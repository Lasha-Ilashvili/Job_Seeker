package com.example.job_seeker.presentation.mapper.user_job

import com.example.job_seeker.domain.model.user_jobs.GetUserJob
import com.example.job_seeker.presentation.model.user_jobs.UserJob


fun GetUserJob.toPresentation() = UserJob(
    id = id,
    title = title,
    company = company,
    date = date,
    category = category,
    contractType = contractType,
    contractTime = contractTime,
    salary = salary,
    description = description,
    location = location,
    redirectUrl = redirectUrl,
)