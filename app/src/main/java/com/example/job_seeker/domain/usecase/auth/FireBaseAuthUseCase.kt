package com.example.job_seeker.domain.usecase.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class FireBaseAuthUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    operator fun invoke() = flow {
        emit(firebaseAuth.currentUser != null)
    }
}
