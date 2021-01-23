package com.lucianoluzzi.firebase_test.data

import com.android.billingclient.api.*
import com.lucianoluzzi.firebase_test.domain.model.ConsumeProductResult

interface PricingRepository {
    suspend fun getPurchases(purchaseType: String): List<Purchase>?
    suspend fun getProducts(productDetailsParams: SkuDetailsParams): List<SkuDetails>?
    suspend fun consumeProduct(consumeParams: ConsumeParams): ConsumeProductResult
    suspend fun acknowledgePurchase(acknowledgePurchaseParams: AcknowledgePurchaseParams): BillingResult
}