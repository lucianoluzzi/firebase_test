package com.lucianoluzzi.firebase_test.domain

import com.lucianoluzzi.firebase_test.data.User

interface SignInUseCase {
    suspend fun signIn(password: String, email: String): User?
}