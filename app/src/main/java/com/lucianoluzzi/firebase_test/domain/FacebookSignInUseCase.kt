package com.lucianoluzzi.firebase_test.domain

import com.facebook.AccessToken

interface FacebookSignInUseCase {
    fun signIn(token: AccessToken)
}