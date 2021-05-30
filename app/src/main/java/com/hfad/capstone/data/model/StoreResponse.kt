package com.hfad.capstone.data.model

import com.google.gson.annotations.SerializedName

data class StoreResponse(
        @field:SerializedName("store")
        val store: Store
)
