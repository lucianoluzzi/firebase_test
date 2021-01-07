package com.lucianoluzzi.firebase_test.domain

import com.lucianoluzzi.firebase_test.data.User

interface GetUserUseCase {
    fun getCurrentUser(): User?
}