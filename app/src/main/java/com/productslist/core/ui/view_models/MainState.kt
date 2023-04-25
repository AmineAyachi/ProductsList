package com.productslist.core.ui.view_models

import com.productslist.core.data.remote.dto.responses.ProductsReviews
import com.productslist.core.domain.model.Product
import com.productslist.core.domain.model.Review


data class MainState(
    var products: List<Product> = emptyList(),
    var reviews: List<ProductsReviews> = emptyList(),
    var isProductsloading:Boolean= false,
    var isReviewsloading:Boolean= false,
    var productError:Boolean= false,
    var reviewError:Boolean= false,
)