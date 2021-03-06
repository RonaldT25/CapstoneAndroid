package com.hfad.capstone.data.model

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
        val negative:ArrayList<String>,
        val neutral:ArrayList<String>,
        val positive:ArrayList<String>,
        @field:SerializedName("image")
        val image: ImageCrawl
)
