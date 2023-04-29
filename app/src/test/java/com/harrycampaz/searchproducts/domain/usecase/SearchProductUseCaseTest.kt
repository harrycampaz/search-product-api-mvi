package com.harrycampaz.searchproducts.domain.usecase

import com.harrycampaz.searchproducts.data.models.toListProductEntity
import com.harrycampaz.searchproducts.domain.repository.IProductRepository
import com.harrycampaz.searchproducts.utils.getFakeProductResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SearchProductUseCaseTest {

    private lateinit var userCase: SearchProductUseCase
    private lateinit var repository: IProductRepository

    private val queryString = "productName"
    private val productsEntity = getFakeProductResponse().toListProductEntity()

    @Before
    fun setup(){
        repository = mockk(relaxed = true)
        userCase = SearchProductUseCase(repository)
    }

    @Test
    fun `GIVEN query WHEN invoke repository product THEN returns a list of ProductEntity`() = runTest{

        coEvery { repository.searchProducts(queryString) } returns  Result.success(productsEntity)

        val result = userCase.searchProduct(queryString)

        assertEquals(productsEntity, result.getOrNull())
    }
}