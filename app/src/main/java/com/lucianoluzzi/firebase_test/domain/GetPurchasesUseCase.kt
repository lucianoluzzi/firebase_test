package com.lucianoluzzi.firebase_test.domain

import com.android.billingclient.api.Purchase

interface GetPurchasesUseCase {
    suspend fun getPurchasedSubscriptions(): List<Purchase>?
}