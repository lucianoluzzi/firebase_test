package com.lucianoluzzi.firebase_test.domain

import com.android.billingclient.api.BillingClient

interface GetBillingClientUseCase {
    fun getBillingClient(): BillingClient
}