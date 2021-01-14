package com.lucianoluzzi.firebase_test.data

import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.android.billingclient.api.SkuDetailsParams
import com.lucianoluzzi.firebase_test.domain.BillingClientProvider
import com.lucianoluzzi.firebase_test.util.connect
import com.lucianoluzzi.firebase_test.util.getProducts

class PricingRepositoryImpl(
    billingClientProvider: BillingClientProvider
) : PricingRepository {

    private val billingClient = billingClientProvider.billingClient

    override suspend fun getPurchases(purchaseType: String): List<Purchase>? {
        val isConnected = billingClient.connect()
        if (!isConnected) {
            return null
        }

        return billingClient.queryPurchases(purchaseType).purchasesList
    }

    override suspend fun getProducts(
        productDetailsParams: SkuDetailsParams
    ): List<SkuDetails>? {
        val isConnected = billingClient.connect()
        if (!isConnected) {
            return null
        }

        return billingClient.getProducts(productDetailsParams)
    }
}