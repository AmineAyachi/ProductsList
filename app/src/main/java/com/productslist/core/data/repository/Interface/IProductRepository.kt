package com.productslist.core.ui.data.repository.Interface

import com.newsapp.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    fun getAll (): Flow<Resource<News>>
}