package com.lucianoluzzi.firebase_test

import com.android.billingclient.api.PurchasesUpdatedListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lucianoluzzi.firebase_test.data.PricingRepository
import com.lucianoluzzi.firebase_test.data.PricingRepositoryImpl
import com.lucianoluzzi.firebase_test.data.UserRepository
import com.lucianoluzzi.firebase_test.data.UserRepositoryImpl
import com.lucianoluzzi.firebase_test.domain.*
import com.lucianoluzzi.firebase_test.viewModel.MainViewModel
import com.lucianoluzzi.firebase_test.viewModel.PricingViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<UserRepository> { UserRepositoryImpl(Firebase.auth) }

    single<GetUserUseCase> { GetUserUseCaseImpl(get()) }

    single<SignInUseCase> { SignInUseCaseImpl(get()) }

    single<GoogleSignInUseCase> { GoogleSignInUseCaseImpl(get()) }

    single<FacebookSignInUseCase> { FacebookSignInUseCaseImpl(get()) }

    single<RegisterUserUseCase> { RegisterUserUseCaseImpl(get()) }

    single { BillingClientProvider(get(), get()) }

    single<PricingRepository> { PricingRepositoryImpl(get()) }

    single<GetPurchasesUseCase> { GetPurchasesUseCaseImpl(get()) }

    single<GetSubscriptionsUseCase> { GetSubscriptionsUseCaseImpl(get()) }

    single<ConsumeUseCase> { ConsumeUseCaseImpl(get()) }

    single<AcknowledgePurchaseUseCase> { AcknowledgePurchaseUseCaseImpl(get()) }

    single<PurchasesUpdatedListener> { BillingUpdateListener() }

    single { BillingUpdateListener() }

    viewModel {
        MainViewModel(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    viewModel {
        PricingViewModel(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}