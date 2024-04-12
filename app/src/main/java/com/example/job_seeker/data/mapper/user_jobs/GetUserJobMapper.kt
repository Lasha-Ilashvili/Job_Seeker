package com.example.job_seeker.data.mapper.user_jobs

import com.example.job_seeker.data.model.user_jobs.UserJobDto
import com.example.job_seeker.domain.model.user_jobs.GetUserJob


fun GetUserJob.toData() = UserJobDto(
    id = id,
    userUid = userUid,
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