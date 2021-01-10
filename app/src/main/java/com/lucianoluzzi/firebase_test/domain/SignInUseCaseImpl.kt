package com.lucianoluzzi.firebase_test.domain

import com.lucianoluzzi.firebase_test.data.UserRepository

class SignInUseCaseImpl(
    private val userRepository: UserRepository
) : SignInUseCase {
    override suspend fun signIn(password: String, email: String) = userRepository.signIn(password, email)
}