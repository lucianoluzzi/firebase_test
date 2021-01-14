package com.lucianoluzzi.firebase_test.domain

import com.android.billingclient.api.SkuDetails

interface GetSubscriptionsUseCase {
    suspend fun getSubscriptions(): List<SkuDetails>?
}