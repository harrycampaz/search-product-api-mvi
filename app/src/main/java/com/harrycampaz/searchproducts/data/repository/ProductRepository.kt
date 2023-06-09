package com.harrycampaz.searchproducts.data.repository

import com.harrycampaz.searchproducts.common.NetworkException
import com.harrycampaz.searchproducts.common.ServerException
import com.harrycampaz.searchproducts.common.UnknownException
import com.harrycampaz.searchproducts.data.network.ProductApi
import com.harrycampaz.searchproducts.data.models.toListProductEntity
import com.harrycampaz.searchproducts.domain.entities.ProductEntity
import com.harrycampaz.searchproducts.domain.repository.IProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApi: ProductApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : IProductRepository {
    override suspend fun searchProducts(queryString: String): Result<List<ProductEntity>> =
        withContext(dispatcher) {
            return@withContext try {
                productApi.searchProducts(queryString).toListProductEntity().let {
                    Result.success(it)
                }
            } catch (e: Exception) {
                when (e) {
                    is IOException -> Result.failure(NetworkException("Error de red"))
                    is HttpException -> Result.failure(
                        ServerException(
                            e.code(),
                            "Error de servidor",
                        )
                    )
                    else -> Result.failure(UnknownException("Error desconocido"))
                }
            }
        }
}
