package com.example.job_seeker.data.common


sealed class Resource<out D : Any> {
    data class Success<out D : Any>(val data: D) : Resource<D>()
    data class Error(val errorMessage: String) : Resource<Nothing>()
    data class Loading(val loading: Boolean) : Resource<Nothing>()
}