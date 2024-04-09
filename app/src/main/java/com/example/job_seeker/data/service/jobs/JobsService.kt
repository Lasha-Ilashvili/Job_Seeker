package com.example.job_seeker.data.service.jobs

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface JobsService {

    @GET("api/jobs/{country}/search/{page}")
    suspend fun getJobs(
        @Path("country") country: String,
        @Path("page") page: Int,
        @Query("results_per_page") size: Int
    ): Response<ResponseBody>
}