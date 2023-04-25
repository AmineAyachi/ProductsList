package com.productslist.core.domain.use_case

import com.newsapp.core.util.Resource
import com.productslist.core.data.repository.Interface.IProductRepository
import com.productslist.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

class GetProduct (  private val repository: IProductRepository
)  {
    val TAG = "GetProduct"
    operator  fun invoke(): Flow<Resource<List<Product>>> {
        val result = repository.getProduct()
        return result
    }
}