package com.productslist.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.productslist.core.domain.model.Brand
import com.productslist.core.domain.model.ImagesUrl
import com.productslist.core.domain.model.Review
import java.io.Serializable

@Entity(
    tableName = "products"
)

data class Product(
    @PrimaryKey val productId: Int,
    val product_name: String,
    val description: String,
    val price: Double,
    val images_url : ImagesUrl,
    val c_brand:Brand,
    val reviews: List<Review>?,
    val is_productSet: Boolean,
    val is_special_brand: Boolean
) : Serializable

