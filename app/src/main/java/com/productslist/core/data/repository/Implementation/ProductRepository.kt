package com.productslist.core.data.repository.Implementation

import android.util.Log
import com.newsapp.core.util.Resource
import com.productslist.core.data.remote.dto.responses.ProductsReviews
import com.productslist.core.data.remote.services.ProductService
import com.productslist.core.data.repository.Interface.IProductRepository
import com.productslist.core.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: ProductService) : IProductRepository {
    private val TAG = "ProductRepository"
    override fun getProduct (): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())

        try {
            Log.e(TAG, "getAll : try");
            val result = api.getProducts()
            //  Log.e(TAG, "getAll : job finished"+ result.body()?.data.toString());
            emit(Resource.Success<List<Product>>(result.body()))
        }catch (e: HttpException ){
            emit(Resource.Error<List<Product>>(message = e.response().toString(),data = null))
            Log.e(TAG, "getAll :"+ e);

        }catch (e: IOException){

            emit(Resource.Error<List<Product>>(message = "Couldn't reach server, check Your internet connection",data = null))
            Log.e(TAG, "Couldn't reach server");
        }

    }



    override fun  getReview(): Flow<Resource<List<ProductsReviews>>> = flow {
        emit(Resource.Loading())

        try {
            Log.e(TAG, "getAll : try");
            val result = api.getReviews()
            //  Log.e(TAG, "getAll : job finished"+ result.body()?.data.toString());
            emit(Resource.Success<List<ProductsReviews>>(result.body()))
        }catch (e: HttpException ){
            emit(Resource.Error<List<ProductsReviews>>(message = e.response().toString(),data = null))
            Log.e(TAG, "getAll :"+ e);

        }catch (e: IOException){

            emit(Resource.Error<List<ProductsReviews>>(message = "Couldn't reach server, check Your internet connection",data = null))
            Log.e(TAG, "Couldn't reach server");
        }

    }

}