package com.hfad.capstone.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Composition(
         val id:Int,
         val compositionName: String,
         val storeId: Int,
         val unit: String
) : Parcelable
