package com.harrycampaz.searchproducts.presentation.ui.searchProduct.viewobject

import android.os.Parcelable
import com.harrycampaz.searchproducts.domain.entities.ProductEntity
import com.harrycampaz.searchproducts.presentation.components.ProductItemVO
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListProductViewObject(
    val products: List<ProductViewObject>
): Parcelable

@Parcelize
data class ProductViewObject(
    val id: String,
    val title: String,
    val price: Double,
    val thumbnail: String,
    val availableQuantity: Int,
): Parcelable

fun List<ProductEntity>.toListViewObject() = map { it.toViewObject() }

fun ProductEntity.toViewObject() = ProductViewObject(
    id = id,
    title = title,
    price = price,
    thumbnail = thumbnail,
    availableQuantity = availableQuantity)

fun ProductViewObject.toItemView() = ProductItemVO(
    title = title,
    price = price,
    thumbnail = thumbnail)