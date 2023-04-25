package com.productslist.core.data.remote.services



import com.productslist.core.data.remote.dto.responses.ProductsReviews
import com.productslist.core.domain.model.Product
import com.productslist.core.domain.model.Review
import retrofit2.Response
import retrofit2.http.GET


interface ProductService {


    @GET("items.json")
    suspend fun getProducts(
    ): Response<List<Product>>

    @GET("reviews.json")
    suspend fun getReviews(
    ): Response<List<ProductsReviews>>
}