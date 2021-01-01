package com.example.firebase_test.data

interface UserRepository {
    fun getUser(): User?
    fun signIn(password: String, email: String)
    fun register(userName: String, password: String, email: String)
}