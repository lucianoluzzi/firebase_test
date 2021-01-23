package com.lucianoluzzi.firebase_test.domain

import com.android.billingclient.api.Purchase

interface ConsumeUseCase {
    suspend fun consumePurchase(purchase: Purchase)
}