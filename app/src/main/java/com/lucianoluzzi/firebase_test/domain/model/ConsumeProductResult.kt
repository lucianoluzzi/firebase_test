package com.lucianoluzzi.firebase_test.domain.model

import com.android.billingclient.api.BillingResult

data class ConsumeProductResult(
    val billingResult: BillingResult,
    val purchaseToken: String
)