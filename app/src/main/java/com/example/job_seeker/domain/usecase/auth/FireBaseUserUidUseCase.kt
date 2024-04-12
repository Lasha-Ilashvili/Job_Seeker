package com.example.job_seeker.domain.usecase.auth

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject


class FireBaseUserUidUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    operator fun invoke() = firebaseAuth.currentUser?.uid ?: ""
}