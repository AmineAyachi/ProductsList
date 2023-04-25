package com.productslist.core.data.remote.dto.responses

import com.productslist.core.domain.model.Review

data class ProductsReviews(
    val product_id: Int,
    val hide: Boolean?,
    val reviews: List<Review>
) {

}