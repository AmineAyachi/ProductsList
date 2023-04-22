package com.productslist.core.ui.data.remote.services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {


    @GET("items.json")
    suspend fun getProducts(
    ): Response<News>

    @GET("reviews.json")
    suspend fun getReviews(
    ): Response<News>
}