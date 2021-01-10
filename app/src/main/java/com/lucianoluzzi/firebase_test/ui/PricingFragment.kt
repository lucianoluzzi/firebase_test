package com.lucianoluzzi.firebase_test.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.BillingResponseCode.OK
import com.android.billingclient.api.BillingClient.BillingResponseCode.USER_CANCELED
import com.android.billingclient.api.Purchase.PurchaseState.PURCHASED
import com.lucianoluzzi.firebase_test.databinding.PricingFragmentBinding

class PricingFragment : Fragment() {
    private lateinit var billingClient: BillingClient
    private val TAG = "PricingFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up the billing client
        billingClient = BillingClient
            .newBuilder(requireContext())
            .enablePendingPurchases()
            .setListener { billingResult, purchases ->

            }
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "Billing client successfully set up")
                    queryPurchases()
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.d(TAG, "Billing service disconnected")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return PricingFragmentBinding.inflate(inflater).root
    }

    private fun querySubscriptions() {
        val skuListToQuery = ArrayList<String>().apply {
            add("assinatura_teste_1")
            add("assinatura_trimestral_1")
            add("assinatura_anual_1")
        }

        // Here is where we can add more product IDs to query for based on
        //   what was set up in the Play Console.

        val params = SkuDetailsParams.newBuilder()
        params
            .setSkusList(skuListToQuery)
            .setType(BillingClient.SkuType.SUBS)
        // SkuType.INAPP refers to 'managed products' or one time purchases.
        // To query for subscription products, you would use SkuType.SUBS.

        billingClient.querySkuDetailsAsync(
            params.build()
        ) { result, skuDetails ->
            Log.d(TAG, "onSkuDetailsResponse ${result.responseCode}")
            if (skuDetails != null) {
                for (skuDetail in skuDetails) {
                    Log.d(TAG, skuDetail.toString())
                }
            } else {
                Log.d(TAG, "No skus found from query")
            }
        }
    }

    fun queryPurchases() {
        if (!billingClient.isReady) {
            Log.e(TAG, "queryPurchases: BillingClient is not ready")
        }
        // Query for existing in app products that have been purchased. This does NOT include subscriptions.
        val result = billingClient.queryPurchases(BillingClient.SkuType.SUBS)
        if (result.purchasesList.isNullOrEmpty()) {
            Log.i(TAG, "No existing in app purchases found.")
            querySubscriptions()
        } else {
            Log.i(TAG, "Existing purchases: ${result.purchasesList}")
        }
    }

    fun launchPurchaseFlow(skuDetails: SkuDetails) {
        val flowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetails)
            .build()
        val responseCode = billingClient.launchBillingFlow(requireActivity(), flowParams)
        Log.i(TAG, "launchPurchaseFlow result $responseCode")
    }

    // Google Play calls this method to propogate the result of the purchase flow
    fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase?>?) {
        if (billingResult.responseCode == OK && purchases != null) {
            for (purchase in purchases) {
                purchase?.let {
                    handlePurchase(it)
                }
            }
        } else if (billingResult.responseCode == USER_CANCELED) {
            Log.i(TAG, "User cancelled purchase flow.")
        } else {
            Log.i(TAG, "onPurchaseUpdated error: ${billingResult.responseCode}")
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        // If your app has a server component, first verify the purchase by checking that the
        // purchaseToken hasn't already been used.

        // If purchase was a consumable product (a product you want the user to be able to buy again)
        handleConsumableProduct(purchase)

        // If purchase was non-consumable product
        handleNonConsumableProduct(purchase)
    }

    private fun handleConsumableProduct(purchase: Purchase) {
        val consumeParams =
            ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()

        billingClient.consumeAsync(consumeParams) { billingResult, purchaseToken ->
            if (billingResult.responseCode == OK) {
                // Handle the success of the consume operation.
            }
        }
    }

    private fun handleNonConsumableProduct(purchase: Purchase) {
        if (purchase.purchaseState == PURCHASED) {
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                billingClient.acknowledgePurchase(acknowledgePurchaseParams.build())
            }
        }
    }
}