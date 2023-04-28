package com.harrycampaz.searchproducts.domain.repository

import com.harrycampaz.searchproducts.domain.entities.ProductEntity

interface IProductRepository {
    suspend fun searchProducts(queryString: String): Result<List<ProductEntity>>
}