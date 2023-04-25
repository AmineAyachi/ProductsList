package com.productslist.core.data.repository.Interface

import com.newsapp.core.util.Resource
import com.productslist.core.data.remote.dto.responses.ProductsReviews
import com.productslist.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    fun getProduct (): Flow<Resource<List<Product>>>
    fun getReview (): Flow<Resource<List<ProductsReviews>>>
}