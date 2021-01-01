package com.example.firebase_test.domain

interface SignInUseCase {
    fun signIn(password: String, email: String)
}