package com.example.job_seeker.data.service.job

import com.example.job_seeker.data.model.jobs.JobDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface JobService {

    @GET("api/jobs/{country}/search/{page}")
    suspend fun getJob(
        @Path("country") country: String = "us",
        @Path("page") page: Int = 1,
        @Query("what") jobId: String
    ): Response<JobDto>
}