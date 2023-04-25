package com.newsapp.core.di

import android.app.Application
import android.content.Context
import com.newsapp.core.data.remote.RetrofitInstance
import com.productslist.core.data.remote.services.ProductService
import com.productslist.core.data.repository.Implementation.ProductRepository
import com.productslist.core.data.repository.Interface.IProductRepository
import com.productslist.core.domain.use_case.GetProduct
import com.productslist.core.domain.use_case.GetReview
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //    @Singleton
//    @Provides
//    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideProductRepository(api: ProductService): IProductRepository = ProductRepository(api)

    @Provides
    @Singleton
    fun provideGetProductsUseCase(repository: ProductRepository): GetProduct {
        return GetProduct(repository)
    }

    @Provides
    @Singleton
    fun provideGetReviewsUseCase(repository: ProductRepository): GetReview {
        return GetReview(repository)
    }


    @Provides
    @Singleton
    fun productApi(): ProductService {
        return RetrofitInstance.ProductsApi
    }

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext
}
