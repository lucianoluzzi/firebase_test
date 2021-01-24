package com.lucianoluzzi.firebase_test.domain

import com.android.billingclient.api.BillingClient
import com.lucianoluzzi.firebase_test.data.PricingRepository

class GetBillingClientUseCaseImpl(
    private val repository: PricingRepository
) : GetBillingClientUseCase {
    override fun getBillingClient() = repository.getBillingClient()
}