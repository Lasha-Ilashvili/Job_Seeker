package com.example.job_seeker.domain.usecase.auth

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject


class FireBaseAuthLogOutUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    operator fun invoke() = firebaseAuth.signOut()
}
