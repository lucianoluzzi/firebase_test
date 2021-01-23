package com.lucianoluzzi.firebase_test.domain

import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase

interface AcknowledgePurchaseUseCase {
    suspend fun acknowledgePurchase(purchase: Purchase): BillingResult?
}