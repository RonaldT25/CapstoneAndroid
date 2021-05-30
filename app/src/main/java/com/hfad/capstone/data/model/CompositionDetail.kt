package com.hfad.capstone.data.model

data class CompositionDetail(
        val id: Int,
        val compositionId: Int,
        val productId: Int,
        val amount: Float,
        val composition: Composition,
        val product: Product
)
