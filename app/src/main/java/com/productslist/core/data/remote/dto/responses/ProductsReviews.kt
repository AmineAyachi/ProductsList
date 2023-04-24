package com.productslist.core.domain.model

data class ProductsReviews(
    val product_id: Int,
    val hide: Boolean?,
    val reviews: List<Review>
) {

}