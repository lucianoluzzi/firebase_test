package com.example.firebase_test.domain

import com.example.firebase_test.data.User
import com.example.firebase_test.data.UserRepository

class GetUserUseCaseImpl(
    private val userRepository: UserRepository
) : GetUserUseCase {

    override fun getCurrentUser(): User? = userRepository.getUser()
}