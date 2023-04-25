package com.productslist.core.domain.use_case

import com.newsapp.core.util.Resource
import com.productslist.core.data.remote.dto.responses.ProductsReviews
import com.productslist.core.data.repository.Interface.IProductRepository
import kotlinx.coroutines.flow.Flow


class GetReview (  private val repository: IProductRepository
)  {
    val TAG = "GetReview"
    operator  fun invoke(): Flow<Resource<List<ProductsReviews>>> {
        val result = repository.getReview()
        return result
    }
}