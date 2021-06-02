package com.hfad.capstone.data.model

import com.google.gson.annotations.Expose

data class MonthlyExpenses(
    @Expose
    val result:Map<String,Int>
)
