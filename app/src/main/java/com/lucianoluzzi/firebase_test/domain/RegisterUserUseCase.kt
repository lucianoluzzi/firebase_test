package com.lucianoluzzi.firebase_test.domain

interface RegisterUserUseCase {
    fun register(userName: String, password: String, email: String)
}