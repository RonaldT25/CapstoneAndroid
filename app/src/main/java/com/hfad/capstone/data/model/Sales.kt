package com.hfad.capstone.data.model

import com.google.gson.annotations.SerializedName

data class Sales(
        val date:String,
        @SerializedName("monthly_expenses")
        val result:Map<String, Float>
)
