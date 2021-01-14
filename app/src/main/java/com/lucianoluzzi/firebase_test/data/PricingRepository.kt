package com.lucianoluzzi.firebase_test.data

import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.android.billingclient.api.SkuDetailsParams

interface PricingRepository {
    suspend fun getPurchases(purchaseType: String): List<Purchase>?
    suspend fun getProducts(productDetailsParams: SkuDetailsParams): List<SkuDetails>?
}