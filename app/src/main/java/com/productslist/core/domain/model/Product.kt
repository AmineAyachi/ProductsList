package com.productslist.core.domain.model

import androidx.room.PrimaryKey


data class Product(
    val product_id: Int,
    val product_name: String,
    val description: String,
    val price: Double,
    val images_url : ImagesUrl,
    val c_brand:Brand,
    var reviews: List<Review>?,
    val is_productSet: Boolean,
    val is_special_brand: Boolean
)

