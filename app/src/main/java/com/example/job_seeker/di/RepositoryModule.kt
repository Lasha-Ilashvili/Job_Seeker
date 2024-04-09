package com.example.job_seeker.di

import com.example.job_seeker.data.common.HandleResponse
import com.example.job_seeker.data.repository.jobs.JobsRepositoryImpl
import com.example.job_seeker.data.service.jobs.JobsService
import com.example.job_seeker.domain.repository.jobs.JobsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideJobsRepository(
        jobsService: JobsService,
        handleResponse: HandleResponse
    ): JobsRepository {
        return JobsRepositoryImpl(
            handleResponse = handleResponse,
            jobsService = jobsService
        )
    }
}