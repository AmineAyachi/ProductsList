package com.productslist.core.ui.adapter

import com.productslist.core.domain.model.Product
import com.productslist.core.domain.model.Review

interface sortListener {
    fun onItemClick(product: Product, reviews: List<Review>)
}