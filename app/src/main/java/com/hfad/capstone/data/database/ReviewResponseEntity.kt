package com.hfad.capstone.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.hfad.capstone.data.model.ImageCrawl

@Entity(tableName = "review")
data class ReviewResponseEntity (
        @PrimaryKey val productId:Int,
        val negativeReview:ArrayList<String>,
        val neutralReview:ArrayList<String>,
        val positiveReview:ArrayList<String>,
        @field:SerializedName("image")
         @Embedded
        val image: ImageCrawl
)

class ReviewTypeConverter{
    @TypeConverter
    fun fromString(value: String?): ArrayList<String>{
        val listType = object : TypeToken<ArrayList<String>>(){}.type
        return Gson().fromJson(value,listType)
    }
    @TypeConverter
    fun fromArrayList(value: ArrayList<String?>): String{
        return Gson().toJson(value)
    }

}