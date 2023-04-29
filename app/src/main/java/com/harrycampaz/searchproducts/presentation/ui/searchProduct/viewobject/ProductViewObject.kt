package com.harrycampaz.searchproducts.presentation.ui.searchProduct.viewobject

import com.harrycampaz.searchproducts.domain.entities.ProductEntity

data class ProductViewObject(
    val id: String,
    val title: String,
    val price: Double,
    val thumbnail: String,
    val available_quantity: Int,
)

fun ProductEntity.toViewObject() = ProductViewObject(
    id = id,
    title = title,
    price = price,
    thumbnail = thumbnail,
    available_quantity = available_quantity)