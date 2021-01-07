package com.lucianoluzzi.firebase_test.domain

import com.lucianoluzzi.firebase_test.data.User
import com.lucianoluzzi.firebase_test.data.UserRepository

class GetUserUseCaseImpl(
    private val userRepository: UserRepository
) : GetUserUseCase {

    override fun getCurrentUser(): User? = userRepository.getUser()
}