package com.example.firebase_test.domain

import com.example.firebase_test.data.UserRepository
import com.google.firebase.auth.AuthCredential

class GoogleSignInUseCaseImpl(
    private val userRepository: UserRepository
) : GoogleSignInUseCase {

    override fun signIn(authCredential: AuthCredential) = userRepository.signIn(authCredential)
}