package com.productslist.core.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.productslist.core.data.local.entities.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(product: Product): Long

    @Query("SELECT * FROM products")
    fun getAllAProducts(): LiveData<List<Product>>

    @Delete
    suspend fun deleteProducts(product: Product)
}