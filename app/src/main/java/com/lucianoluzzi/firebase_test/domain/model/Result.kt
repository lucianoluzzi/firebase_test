package com.lucianoluzzi.firebase_test.domain.model

import com.lucianoluzzi.firebase_test.data.User

sealed class Result {
    data class Fail(val error: Exception? = null) : Result()
    data class Success(val user: User) : Result()
}