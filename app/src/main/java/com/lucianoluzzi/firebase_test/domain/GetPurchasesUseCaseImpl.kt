package com.lucianoluzzi.firebase_test.domain

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import com.lucianoluzzi.firebase_test.data.PricingRepository

class GetPurchasesUseCaseImpl(
    private val pricingRepository: PricingRepository
) : GetPurchasesUseCase {

    override fun getPurchasedSubscriptions(): List<Purchase>? = pricingRepository.getPurchases(
        BillingClient.SkuType.SUBS
    )
}