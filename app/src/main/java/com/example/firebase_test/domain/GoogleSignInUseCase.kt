package com.example.firebase_test.domain

import com.google.firebase.auth.AuthCredential

interface GoogleSignInUseCase {
    fun signIn(authCredential: AuthCredential)
}