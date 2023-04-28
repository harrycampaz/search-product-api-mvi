package com.harrycampaz.searchproducts.data

data class ProductResponse(
    val results: List<ProductModel>
)

data class ProductModel(
    val id: Int,
    val title: String,
    val price: Double,
    val thumbnail: String,
    val available_quantity: Double,
)