package com.harrycampaz.searchproducts.data.repository

import com.harrycampaz.searchproducts.common.NetworkException
import com.harrycampaz.searchproducts.common.ServerException
import com.harrycampaz.searchproducts.common.UnknownException
import com.harrycampaz.searchproducts.data.network.ProductApi
import com.harrycampaz.searchproducts.data.models.toListProductEntity
import com.harrycampaz.searchproducts.utils.getFakeProductResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
internal class ProductRepositoryTest {
    private lateinit var productApi: ProductApi
    private lateinit var productRepository: ProductRepository
    private val productResponse = getFakeProductResponse()
    private val queryString = "productName"

    @Before
    fun setup() {
        productApi = mockk(relaxed = true)
        productRepository = ProductRepository(productApi)
    }


    @Test
    fun `GIVEN query  WHEN ProductApi THEN returns a list of ProductEntity`() = runTest {
        // Given

        coEvery { productApi.searchProducts(queryString) } returns productResponse

        // When
        val result = productRepository.searchProducts(queryString)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(productResponse.toListProductEntity(), result.getOrNull())
    }

    @Test
    fun `GIVEN query WHEN ProductApi throws IOException THEN returns Result failure with NetworkException`() =
        runTest {
            // Given

            val expectedException = IOException("Error de red")
            coEvery { productApi.searchProducts(queryString) } throws expectedException

            // When
            val result = productRepository.searchProducts(queryString)

            // Then
            assertTrue(result.isFailure)
            assertTrue(result.exceptionOrNull() is NetworkException)
            assertEquals(expectedException.message, result.exceptionOrNull()?.message)
        }

    @Test
    fun `GIVEN query WHEN ProductApi throws HttpException THEN returns Result failure with ServerException`() =
        runTest {
            // Given

            val expectedException = HttpException(mockk<retrofit2.Response<*>>().apply {
                coEvery { code() } returns 500
                coEvery { message() } returns "Error de servidor"
            })


            coEvery { productApi.searchProducts(queryString) } throws expectedException

            // When
            val result = productRepository.searchProducts(queryString)

            // Then
            assertTrue(result.isFailure)
            assertTrue(result.exceptionOrNull() is ServerException)
            assertEquals(expectedException.message(), result.exceptionOrNull()?.message)
        }

    @Test
    fun `GIVEN query WHEN ProductApi throws RuntimeException THEN returns Result failure with UnknownException`() =
        runTest {
            // Given
            val expectedException = RuntimeException("Error desconocido")
            coEvery { productApi.searchProducts(queryString) } throws expectedException

            // When
            val result = productRepository.searchProducts(queryString)

            // Then
            assertTrue(result.isFailure)
            assertTrue(result.exceptionOrNull() is UnknownException)
            assertEquals(expectedException.message, result.exceptionOrNull()?.message)
        }
}