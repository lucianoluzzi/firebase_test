package com.example.firebase_test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebase_test.data.User
import com.example.firebase_test.domain.GetUserUseCase
import com.example.firebase_test.domain.GoogleSignInUseCase
import com.example.firebase_test.domain.RegisterUserUseCase
import com.example.firebase_test.domain.SignInUseCase
import com.google.firebase.auth.AuthCredential

class MainViewModel(
    getUserUseCase: GetUserUseCase,
    private val singInUseCase: SignInUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _userLiveData = MutableLiveData<User?>()
    val userLiveData: LiveData<User?> = _userLiveData

    init {
        _userLiveData.value = getUserUseCase.getCurrentUser()
    }

    fun onSignIn(password: String, email: String) {
        singInUseCase.signIn(
            password = password,
            email = email
        )
    }

    fun onGoogleSignIn(authCredential: AuthCredential, displayName: String) {
        googleSignInUseCase.signIn(authCredential, displayName)
    }

    fun onRegister(userName: String, password: String, email: String) {
        registerUserUseCase.register(
            userName = userName,
            password = password,
            email = email
        )
    }
}