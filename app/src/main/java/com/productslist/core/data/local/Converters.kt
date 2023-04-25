package com.newsapp.core.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.productslist.core.domain.model.Brand
import com.productslist.core.domain.model.ImagesUrl
import com.productslist.core.domain.model.Review
import java.util.Date

class Converters {
    private val gson = Gson()
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return when (timestamp) {
            null -> null
            else -> Date(timestamp)
        }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromReviewList(reviews: List<Review>?): String? {
        return reviews?.let { gson.toJson(reviews) }
    }

    @TypeConverter
    fun toReviewList(reviewString: String?): List<Review>? {
        return reviewString?.let {
            val type = object : TypeToken<List<Review>>() {}.type
            gson.fromJson<List<Review>>(reviewString, type)
        }
    }

    @TypeConverter
    fun fromImagesUrl(imagesUrl: ImagesUrl?): String? {
        return imagesUrl?.let { gson.toJson(imagesUrl) }
    }

    @TypeConverter
    fun toImageUrl(imagesUrl: String?): ImagesUrl? {
        return imagesUrl?.let {
            val type = object : TypeToken<ImagesUrl>() {}.type
            gson.fromJson<ImagesUrl>(imagesUrl, type)
        }
    }


    @TypeConverter
    fun fromBrand(brand: Brand?): String? {
        return brand?.let { gson.toJson(brand) }
    }

    @TypeConverter
    fun toBrand(brand: String?): Brand? {
        return brand?.let {
            val type = object : TypeToken<Brand>() {}.type
            gson.fromJson<Brand>(brand, type)
        }
    }


}