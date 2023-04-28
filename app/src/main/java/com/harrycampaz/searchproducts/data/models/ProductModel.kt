package com.harrycampaz.searchproducts.data

import com.harrycampaz.searchproducts.domain.entities.ProductEntity

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

fun ProductResponse.toListProductEntity(): List<ProductEntity> {
    return this.results.map {
        ProductEntity(
            id = it.id,
            title = it.title,
            price = it.price,
            thumbnail = it.thumbnail,
            available_quantity = it.available_quantity,
        )
    }
}