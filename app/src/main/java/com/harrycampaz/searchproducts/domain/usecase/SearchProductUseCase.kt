package com.harrycampaz.searchproducts.domain.usecase

import com.harrycampaz.searchproducts.domain.entities.ProductEntity
import com.harrycampaz.searchproducts.domain.repository.IProductRepository
import javax.inject.Inject

class SearchProductUseCase @Inject constructor(
    private val repository: IProductRepository
) {
    suspend fun searchProduct(queryName: String): Result<List<ProductEntity>>{
        return repository.searchProducts(queryName)
    }
}