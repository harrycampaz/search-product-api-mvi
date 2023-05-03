package com.harrycampaz.searchproducts.domain.entities

data class ProductEntity(
    val id: String,
    val title: String,
    val price: Double,
    val thumbnail: String,
    val availableQuantity: Int,
)
