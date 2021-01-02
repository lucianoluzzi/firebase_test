package com.example.firebase_test

import com.example.firebase_test.data.UserRepository
import com.example.firebase_test.data.UserRepositoryImpl
import com.example.firebase_test.domain.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<UserRepository> { UserRepositoryImpl(Firebase.auth) }

    single<GetUserUseCase> { GetUserUseCaseImpl(get()) }

    single<SignInUseCase> { SignInUseCaseImpl(get()) }

    single<GoogleSignInUseCase> { GoogleSignInUseCaseImpl(get()) }

    single<RegisterUserUseCase> { RegisterUserUseCaseImpl(get()) }

    viewModel {
        MainViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
}