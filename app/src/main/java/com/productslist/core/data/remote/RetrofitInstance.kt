package com.newsapp.core.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.newsapp.core.util.Constants.Companion.BASE_URL
import com.productslist.core.ui.data.remote.services.ProductService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Field

class RetrofitInstance {
    companion object {
    private val retrofit by lazy {

        val logging = HttpLoggingInterceptor()
        val gson : Gson by lazy {
            GsonBuilder().setFieldNamingStrategy { f: Field -> f.name.lowercase() }.create()
        }

        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build()

    }


    val ProductsApi by lazy {
        retrofit.create(ProductService::class.java)
    }
    }

}