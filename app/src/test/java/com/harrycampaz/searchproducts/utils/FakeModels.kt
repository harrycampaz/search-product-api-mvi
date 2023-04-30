package com.harrycampaz.searchproducts.utils

import com.harrycampaz.searchproducts.data.models.ProductModel
import com.harrycampaz.searchproducts.data.models.ProductResponse

fun getFakeProductResponse() = ProductResponse(
    listOf(
        ProductModel("123", "Product 1", 10.0, "thumbnail", 10),
        ProductModel("124", "Product 1", 14.0, "thumbnail", 103),
    )
)