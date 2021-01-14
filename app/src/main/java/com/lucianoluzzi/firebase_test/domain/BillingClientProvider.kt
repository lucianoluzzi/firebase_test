package com.lucianoluzzi.firebase_test.domain

import android.content.Context
import com.android.billingclient.api.BillingClient

class BillingClientProvider(context: Context) {
    val billingClient = BillingClient
        .newBuilder(context)
        .enablePendingPurchases()
        .build()
}