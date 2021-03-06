package com.lucianoluzzi.firebase_test.data

import com.google.firebase.auth.AuthCredential

interface UserRepository {
    fun getUser(): User?
    suspend fun signIn(password: String, email: String): User?
    fun signIn(authCredential: AuthCredential, displayName: String? = null)
    fun register(userName: String, password: String, email: String)
}