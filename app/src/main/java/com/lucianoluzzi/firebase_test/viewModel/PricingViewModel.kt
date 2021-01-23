package com.lucianoluzzi.firebase_test.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.lucianoluzzi.firebase_test.domain.ConsumeUseCase
import com.lucianoluzzi.firebase_test.domain.GetPurchasesUseCase
import com.lucianoluzzi.firebase_test.domain.GetSubscriptionsUseCase
import kotlinx.coroutines.launch

class PricingViewModel(
    getPurchasesUseCase: GetPurchasesUseCase,
    private val getSubscriptionsUseCase: GetSubscriptionsUseCase,
    private val consumeUseCase: ConsumeUseCase
) : ViewModel() {

    private val _purchasedSubscriptionsLiveData = MutableLiveData<List<Purchase>?>()
    val purchasedSubscriptionsLiveData: LiveData<List<Purchase>?> = _purchasedSubscriptionsLiveData

    private val _subscriptionsLiveData = MutableLiveData<List<SkuDetails>?>()
    val subscriptionsLiveData: LiveData<List<SkuDetails>?> = _subscriptionsLiveData

    init {
        viewModelScope.launch {
            _purchasedSubscriptionsLiveData.value = getPurchasesUseCase.getPurchasedSubscriptions()
        }
    }

    fun getSubscriptions() {
        viewModelScope.launch {
            val subscriptions = getSubscriptionsUseCase.getSubscriptions()
            _subscriptionsLiveData.value = subscriptions
        }
    }

    fun consumeProduct(purchase: Purchase) {
        viewModelScope.launch {
            consumeUseCase.consumePurchase(purchase)
        }
    }
}