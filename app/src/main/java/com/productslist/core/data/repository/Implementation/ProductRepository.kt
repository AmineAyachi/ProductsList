package com.productslist.core.ui.data.repository.Implementation

import android.util.Log
import com.newsapp.core.data.repository.Interface.INewsRepository
import com.newsapp.core.util.Resource
import com.productslist.core.ui.data.remote.services.ProductService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: ProductService) : INewsRepository {
    private val TAG = "ProductRepository"
    override fun getAll (): Flow<Resource<News>> = flow {
        emit(Resource.Loading())

        try {
            Log.e(TAG, "getAll : try");
            val result = api.getBreakingNews()
            //  Log.e(TAG, "getAll : job finished"+ result.body()?.data.toString());
            emit(Resource.Success<News>(result.body()))
        }catch (e: HttpException ){
            emit(Resource.Error<News>(message = e.response().toString(),data = null))
            Log.e(TAG, "getAll :"+ e);

        }catch (e: IOException){

            emit(Resource.Error<News>(message = "Couldn't reach server, check Your internet connection",data = null))
            Log.e(TAG, "Couldn't reach server");
        }

    }

}