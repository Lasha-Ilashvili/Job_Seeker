package com.example.job_seeker.di

import com.example.job_seeker.BuildConfig.APP_ID
import com.example.job_seeker.BuildConfig.APP_KEY
import com.example.job_seeker.BuildConfig.BASE_URL
import com.example.job_seeker.BuildConfig.DEBUG
import com.example.job_seeker.data.common.HandleResponse
import com.example.job_seeker.data.data_source.auth.AuthDataSource
import com.example.job_seeker.data.data_source.jobs.JobsPagingSource
import com.example.job_seeker.data.data_source.user_jobs.UserJobsDataSource
import com.example.job_seeker.data.service.job.JobService
import com.example.job_seeker.data.service.jobs.JobsService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
    }

//    @Singleton
//    @Provides
//    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
//        val builder = OkHttpClient.Builder()
//
//        if (DEBUG) builder.addInterceptor(loggingInterceptor)
//
//        return builder.build()
//    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.addInterceptor { chain ->
            val originalRequest = chain.request()
            val modifiedUrl = originalRequest.url.newBuilder()
                .addQueryParameter("app_id", APP_ID)
                .addQueryParameter("app_key", APP_KEY)
                .build()

            val modifiedRequest = originalRequest.newBuilder()
                .url(modifiedUrl)
                .build()

            chain.proceed(modifiedRequest)
        }

        if (DEBUG) builder.addInterceptor(loggingInterceptor)

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideHandleResponse(): HandleResponse {
        return HandleResponse()
    }

    @Singleton
    @Provides
    fun provideJobsService(retrofit: Retrofit): JobsService {
        return retrofit.create(JobsService::class.java)
    }

    @Singleton
    @Provides
    fun provideFireStore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Singleton
    @Provides
    fun provideDatabase(): FirebaseDatabase {
        return Firebase.database
    }

    @Singleton
    @Provides
    fun provideFireBaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Singleton
    @Provides
    fun provideUserJobsDataSource(fireStore: FirebaseFirestore): UserJobsDataSource {
        return UserJobsDataSource(fireStore = fireStore)
    }

    @Singleton
    @Provides
    fun provideAuthDataSource(firebaseAuth: FirebaseAuth): AuthDataSource {
        return AuthDataSource(firebaseAuth = firebaseAuth)
    }

    @Singleton
    @Provides
    fun provideJobsPagingSource(jobsService: JobsService): JobsPagingSource {
        return JobsPagingSource(service = jobsService)
    }

    @Singleton
    @Provides
    fun provideJobService(retrofit: Retrofit): JobService {
        return retrofit.create(JobService::class.java)
    }
}