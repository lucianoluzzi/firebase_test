package com.lucianoluzzi.firebase_test.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener

class BillingUpdateListener : PurchasesUpdatedListener {

    private val _purchaseUpdateLiveData = MutableLiveData<List<Purchase>?>()
    val purchaseUpdateLiveData: LiveData<List<Purchase>?> = _purchaseUpdateLiveData

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        purchases?.let {
            _purchaseUpdateLiveData.value = it
        }
    }
}