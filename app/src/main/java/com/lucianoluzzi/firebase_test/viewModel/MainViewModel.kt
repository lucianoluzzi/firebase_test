package com.lucianoluzzi.firebase_test.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.google.firebase.auth.AuthCredential
import com.lucianoluzzi.firebase_test.data.User
import com.lucianoluzzi.firebase_test.domain.*
import com.lucianoluzzi.firebase_test.domain.model.Result
import kotlinx.coroutines.launch

class MainViewModel(
    getUserUseCase: GetUserUseCase,
    private val singInUseCase: SignInUseCase,
    private val facebookSignInUseCase: FacebookSignInUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _userLiveData = MutableLiveData<User?>()
    val userLiveData: LiveData<User?> = _userLiveData

    private val _resultLiveData = MutableLiveData<Result>()
    val resultLiveData: LiveData<Result> = _resultLiveData

    init {
        _userLiveData.value = getUserUseCase.getCurrentUser()
    }

    fun onSignIn(password: String, email: String) {
        viewModelScope.launch {
            val userResult = singInUseCase.signIn(
                password = password,
                email = email
            )

            if (userResult != null) {
                _resultLiveData.value = Result.Success(userResult)
            } else {
                _resultLiveData.value = Result.Fail()
            }
        }
    }

    fun onGoogleSignIn(authCredential: AuthCredential, displayName: String) {
        googleSignInUseCase.signIn(authCredential, displayName)
    }

    fun onFacebookSignIn(token: AccessToken) {
        facebookSignInUseCase.signIn(token)
    }

    fun onRegister(userName: String, password: String, email: String) {
        registerUserUseCase.register(
            userName = userName,
            password = password,
            email = email
        )
    }
}