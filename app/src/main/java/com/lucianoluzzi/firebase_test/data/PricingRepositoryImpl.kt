package com.lucianoluzzi.firebase_test.data

import com.android.billingclient.api.*
import com.lucianoluzzi.firebase_test.domain.BillingClientProvider
import com.lucianoluzzi.firebase_test.domain.model.ConsumeProductResult
import com.lucianoluzzi.firebase_test.util.connect
import com.lucianoluzzi.firebase_test.util.consumeProduct
import com.lucianoluzzi.firebase_test.util.getProducts

class PricingRepositoryImpl(
    billingClientProvider: BillingClientProvider
) : PricingRepository {

    private val billingClient = billingClientProvider.billingClient

    override suspend fun getPurchases(purchaseType: String): List<Purchase>? {
        val connectIfNeeded = connectIfNeeded()
        if (!connectIfNeeded)
            return null

        return billingClient.queryPurchases(purchaseType).purchasesList
    }

    override suspend fun getProducts(productDetailsParams: SkuDetailsParams): List<SkuDetails>? {
        val connectIfNeeded = connectIfNeeded()
        if (!connectIfNeeded)
            return null

        return billingClient.getProducts(productDetailsParams)
    }

    private suspend fun connectIfNeeded(): Boolean {
        return billingClient.isReady || billingClient.connect()
    }

    override suspend fun consumeProduct(consumeParams: ConsumeParams): ConsumeProductResult =
        billingClient.consumeProduct(consumeParams)

    override suspend fun acknowledgePurchase(acknowledgePurchaseParams: AcknowledgePurchaseParams): BillingResult =
        billingClient.acknowledgePurchase(acknowledgePurchaseParams)

    override fun getBillingClient() = billingClient
}