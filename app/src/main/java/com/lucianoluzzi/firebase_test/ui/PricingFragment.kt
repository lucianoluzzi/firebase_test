package com.lucianoluzzi.firebase_test.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.lucianoluzzi.firebase_test.databinding.PricingFragmentBinding
import com.lucianoluzzi.firebase_test.viewModel.PricingViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PricingFragment : Fragment() {
    private val TAG = "PricingFragment"

    private lateinit var binding: PricingFragmentBinding
    private val viewModel: PricingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PricingFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.purchasedSubscriptionsLiveData.observe(viewLifecycleOwner) { purchases ->
            if (purchases.isNullOrEmpty()) {
                viewModel.getSubscriptions()
            } else {
                Log.d(TAG, "Existing purchases: ${purchases.size}")
            }
        }
        viewModel.subscriptionsLiveData.observe(viewLifecycleOwner) { subscriptions ->
            if (subscriptions != null) {
                for (subscription in subscriptions) {
                    Log.d(TAG, subscription.toString())
                }
                setProductsList(subscriptions)
            } else {
                Log.d(TAG, "No skus found from query")
                binding.empty.isVisible = true
                binding.loading.isVisible = false
            }
        }
        viewModel.purchasesUpdateLiveData.observe(viewLifecycleOwner) { purchases ->
            acknowledgePurchases(purchases)
        }
    }

    private fun setProductsList(products: List<SkuDetails>) {
        binding.loading.isVisible = false
        binding.empty.isVisible = false
        binding.productsList.adapter = ProductAdapter(requireContext(), products) { skuDetails ->
            launchPurchaseFlow(skuDetails)
        }
    }

    private fun acknowledgePurchases(purchases: List<Purchase>?) {
        purchases?.forEach {
            viewModel.acknowledgePurchase(it)
        }
    }

    private fun launchPurchaseFlow(product: SkuDetails) {
        val flowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(product)
            .build()

        val billingClient = viewModel.getBillingClient()
        val responseCode = billingClient.launchBillingFlow(requireActivity(), flowParams)
        Log.i(TAG, "launchPurchaseFlow result ${responseCode.responseCode}")
    }
}