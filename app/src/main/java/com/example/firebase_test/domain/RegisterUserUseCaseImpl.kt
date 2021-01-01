package com.example.firebase_test.domain

import com.example.firebase_test.data.UserRepository

class RegisterUserUseCaseImpl(
    private val userRepository: UserRepository
) : RegisterUserUseCase {
    override fun register(userName: String, password: String, email: String) =
        userRepository.register(userName, password, email)
}