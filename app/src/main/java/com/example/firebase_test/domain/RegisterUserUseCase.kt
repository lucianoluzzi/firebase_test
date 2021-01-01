package com.example.firebase_test.domain

interface RegisterUserUseCase {
    fun register(userName: String, password: String, email: String)
}