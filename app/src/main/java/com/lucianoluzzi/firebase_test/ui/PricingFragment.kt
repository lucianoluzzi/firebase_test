package com.lucianoluzzi.firebase_test.ui

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
import com.lucianoluzzi.firebase_test.viewModel.PricingViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PricingFragment : Fragment() {
    private lateinit var billingClient: BillingClient
    private val TAG = "PricingFragment"

    private val viewModel: PricingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return PricingFragmentBinding.inflate(inflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.purchasedSubscriptionsLiveData.observe(viewLifecycleOwner) { purchases ->
            if (purchases.isNullOrEmpty()) {
                viewModel.getSubscriptions()
            } else {
                Log.i(TAG, "Existing purchases: ${purchases.size}")
            }
        }
        viewModel.subscriptionsLiveData.observe(viewLifecycleOwner) { subscriptions ->
            if (subscriptions != null) {
                for (subscription in subscriptions) {
                    Log.d(TAG, subscription.toString())
                }
            } else {
                Log.d(TAG, "No skus found from query")
            }
        }
    }

    // Google Play calls this method to propogate the result of the purchase flow
    // need to figure out how to set this in a clean fashion
    private fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase?>?) {
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
        // If purchase was non-consumable product
        handleNonConsumableProduct(purchase)
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

    // When user selects a subscription
    fun launchPurchaseFlow(skuDetails: SkuDetails) {
        val flowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetails)
            .build()
        val responseCode = billingClient.launchBillingFlow(requireActivity(), flowParams)
        Log.i(TAG, "launchPurchaseFlow result $responseCode")
    }
}