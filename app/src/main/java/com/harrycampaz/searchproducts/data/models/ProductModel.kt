package com.harrycampaz.searchproducts.data.models

import com.google.gson.annotations.SerializedName
import com.harrycampaz.searchproducts.domain.entities.ProductEntity

data class ProductResponse(
    val results: List<ProductModel>
)

data class ProductModel(
    val id: String,
    val title: String,
    val price: Double,
    val thumbnail: String,
    @SerializedName("available_quantity")
    val availableQuantity: Int,
)

fun ProductResponse.toListProductEntity(): List<ProductEntity> {
    return this.results.map {
        ProductEntity(
            id = it.id,
            title = it.title,
            price = it.price,
            thumbnail = it.thumbnail,
            availableQuantity = it.availableQuantity,
        )
    }
}