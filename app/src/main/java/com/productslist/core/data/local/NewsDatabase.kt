package com.newsapp.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.productslist.core.data.local.dao.ProductDao
import com.productslist.core.data.local.entities.Product


@Database(
    entities = [Product::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ProductsDatabase: RoomDatabase() {


    abstract fun getPorductDao(): ProductDao


    companion object {
        @Volatile
        private var instance: ProductsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: createDatabase(
                        context
                    ).also {
                        instance = it
                    }
            }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ProductsDatabase::class.java,
            "ProductsDatabase.db"
        ).fallbackToDestructiveMigration().build()

    }
}