package com.lucianoluzzi.firebase_test.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.billingclient.api.SkuDetails
import com.lucianoluzzi.firebase_test.databinding.ItemProductsListBinding

class ProductAdapter(
    private val context: Context,
    private val products: List<SkuDetails>,
    private val onProductClick: (SkuDetails) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductsListBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ProductViewHolder(binding, onProductClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    class ProductViewHolder(
        private val binding: ItemProductsListBinding,
        private val onProductClick: (SkuDetails) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: SkuDetails) {
            with(binding) {
                productName.text = product.title
                periodo.text = product.subscriptionPeriod
                price.text = product.price

                root.setOnClickListener {
                    onProductClick.invoke(product)
                }
            }
        }
    }
}