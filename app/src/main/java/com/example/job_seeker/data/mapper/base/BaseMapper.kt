package com.example.job_seeker.data.mapper.base

import com.example.job_seeker.data.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


fun <Dto : Any, Domain : Any> Flow<Resource<Dto>>.asResource(
    onSuccess: (Dto) -> Domain,
): Flow<Resource<Domain>> {
    return this.map {
        when (it) {
            is Resource.Success -> Resource.Success(data = onSuccess.invoke(it.data))
            is Resource.Error -> Resource.Error(errorMessage = it.errorMessage)
            is Resource.Loading -> Resource.Loading(loading = it.loading)
        }
    }
}