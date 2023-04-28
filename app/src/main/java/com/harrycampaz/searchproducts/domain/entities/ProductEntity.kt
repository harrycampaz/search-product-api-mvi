package com.harrycampaz.searchproducts.domain.entities

data class ProductEntity(
    val id: Int,
    val title: String,
    val price: Double,
    val thumbnail: String,
    val available_quantity: Double,
)
