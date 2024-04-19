package com.example.job_seeker.di

import com.example.job_seeker.data.common.HandleResponse
import com.example.job_seeker.data.data_source.auth.AuthDataSource
import com.example.job_seeker.data.data_source.job_applicants.JobApplicantsDataSource
import com.example.job_seeker.data.data_source.jobs.JobsPagingSource
import com.example.job_seeker.data.data_source.user_jobs.UserJobsDataSource
import com.example.job_seeker.data.repository.auth.AuthRepositoryImpl
import com.example.job_seeker.data.repository.job.JobRepositoryImpl
import com.example.job_seeker.data.repository.job_applicants.JobApplicantsRepositoryImpl
import com.example.job_seeker.data.repository.jobs.JobsRepositoryImpl
import com.example.job_seeker.data.repository.user_jobs.UserJobsRepositoryImpl
import com.example.job_seeker.data.service.job.JobService
import com.example.job_seeker.domain.repository.auth.AuthRepository
import com.example.job_seeker.domain.repository.job.JobRepository
import com.example.job_seeker.domain.repository.job_applicants.JobApplicantsRepository
import com.example.job_seeker.domain.repository.jobs.JobsRepository
import com.example.job_seeker.domain.repository.user_jobs.UserJobsRepository
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
    fun provideJobsRepository(jobsPagingSource: JobsPagingSource): JobsRepository {
        return JobsRepositoryImpl(jobsPagingSource = jobsPagingSource)
    }

    @Singleton
    @Provides
    fun provideUserJobsRepository(
        userJobsDataSource: UserJobsDataSource,
        handleResponse: HandleResponse
    ): UserJobsRepository {
        return UserJobsRepositoryImpl(
            userJobsDataSource = userJobsDataSource,
            handleResponse = handleResponse
        )
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        authDataSource: AuthDataSource,
        handleResponse: HandleResponse
    ): AuthRepository {
        return AuthRepositoryImpl(
            authDataSource = authDataSource,
            handleResponse = handleResponse
        )
    }

    @Singleton
    @Provides
    fun provideJobRepository(
        jobService: JobService,
        handleResponse: HandleResponse
    ): JobRepository {
        return JobRepositoryImpl(
            jobService = jobService,
            handleResponse = handleResponse
        )
    }

    @Singleton
    @Provides
    fun provideJobApplicantsRepository(
        jobApplicantsDataSource: JobApplicantsDataSource
    ): JobApplicantsRepository {
        return JobApplicantsRepositoryImpl(
            jobApplicantsDataSource = jobApplicantsDataSource
        )
    }
}