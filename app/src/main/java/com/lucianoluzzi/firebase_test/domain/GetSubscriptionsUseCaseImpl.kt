package com.lucianoluzzi.firebase_test.domain

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.SkuDetails
import com.android.billingclient.api.SkuDetailsParams
import com.lucianoluzzi.firebase_test.data.PricingRepository

class GetSubscriptionsUseCaseImpl(
    private val pricingRepository: PricingRepository
) : GetSubscriptionsUseCase {

    override suspend fun getSubscriptions(): List<SkuDetails>? {
        val productIDs = ArrayList<String>().apply {
            add("assinatura_teste_1")
            add("assinatura_trimestral_1")
            add("assinatura_anual_1")
        }

        val queryParamsBuilder = SkuDetailsParams.newBuilder().apply {
            setSkusList(productIDs)
            setType(BillingClient.SkuType.SUBS)
        }

        return pricingRepository.getProducts(queryParamsBuilder.build())
    }
}