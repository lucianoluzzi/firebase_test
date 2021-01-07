package com.lucianoluzzi.firebase_test.domain

import com.lucianoluzzi.firebase_test.data.UserRepository
import com.google.firebase.auth.AuthCredential

class GoogleSignInUseCaseImpl(
    private val userRepository: UserRepository
) : GoogleSignInUseCase {

    override fun signIn(authCredential: AuthCredential, displayName: String) =
        userRepository.signIn(authCredential, displayName)
}