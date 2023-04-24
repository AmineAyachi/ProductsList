package com.productslist.come.productslist.core.data.repository.Implementation

import com.newsapp.core.util.Resource
import com.productslist.core.data.remote.dto.responses.ProductsReviews
import com.productslist.core.data.repository.Interface.IProductRepository
import com.productslist.core.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class MockProductRepository : IProductRepository{
    private val products:List<Product> =  mutableListOf<Product>()
    private val productReviews:List<ProductsReviews>  =  mutableListOf<ProductsReviews>()
    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkEroor (value:Boolean){
        shouldReturnNetworkError = value
    }


    override fun getProduct(): Flow<Resource<List<Product>>> {
        return flow {
            if(!shouldReturnNetworkError){
                emit(Resource.Success(products))
            }else{
                emit(Resource.Error<List<Product>>(message = "Couldn't reach server, check Your internet connection",data = null))
            }
        }
    }

    override fun getReview(): Flow<Resource<List<ProductsReviews>>> {
        return flow {
            if(!shouldReturnNetworkError){
                emit(Resource.Success(productReviews))
            }else{
                emit(Resource.Error<List<ProductsReviews>>(message = "Couldn't reach server, check Your internet connection",data = null))
            }
        }
    }


}