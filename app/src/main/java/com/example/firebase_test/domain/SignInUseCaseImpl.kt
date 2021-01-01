package com.example.firebase_test.domain

import com.example.firebase_test.data.UserRepository

class SignInUseCaseImpl(
    private val userRepository: UserRepository
) : SignInUseCase {
    override fun signIn(password: String, email: String) = userRepository.signIn(password, email)
}