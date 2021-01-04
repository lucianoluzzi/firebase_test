package com.example.firebase_test.domain

import com.example.firebase_test.data.UserRepository
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider

class FacebookSignInUseCaseImpl(
    private val userRepository: UserRepository
) : FacebookSignInUseCase {

    override fun signIn(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        userRepository.signIn(credential)
    }
}