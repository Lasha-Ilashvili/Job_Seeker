package com.example.job_seeker.data.service.jobs

import com.example.job_seeker.data.model.jobs.JobDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface JobsService {

    @GET("api/jobs/{country}/search/{page}")
    suspend fun getJobs(
        @Path("country") country: String = "us",
        @Path("page") page: Int,
        @Query("results_per_page") size: Int = 25
    ): Response<JobDto>
}