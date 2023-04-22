package com.newsapp.core.di

import android.app.Application
import android.content.Context
import com.newsapp.core.data.remote.RetrofitInstance
import com.newsapp.core.data.remote.services.NewsService
import com.newsapp.core.data.repository.Implementation.NewsRepository
import com.newsapp.core.data.repository.Interface.INewsRepository
import com.newsapp.core.domain.use_case.GetNews
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
    fun provideNewsRepository(api: NewsService): INewsRepository = NewsRepository(api)

    @Provides
    @Singleton
    fun provideGetNewsUseCase(repository: NewsRepository): GetNews {
        return GetNews(repository)
    }




    @Provides
    @Singleton
    fun newsApi(): NewsService {
        return RetrofitInstance.NewsApi
    }

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext
}
