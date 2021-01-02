package com.example.firebase_test.domain

import com.example.firebase_test.data.User

interface GetUserUseCase {
    fun getCurrentUser(): User?
}