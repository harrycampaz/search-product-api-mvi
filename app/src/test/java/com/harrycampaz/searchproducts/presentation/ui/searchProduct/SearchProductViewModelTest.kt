package com.harrycampaz.searchproducts.presentation.ui.searchProduct

import com.harrycampaz.searchproducts.common.NetworkException
import com.harrycampaz.searchproducts.common.ServerException
import com.harrycampaz.searchproducts.common.UnknownException
import com.harrycampaz.searchproducts.domain.entities.ProductEntity
import com.harrycampaz.searchproducts.domain.usecase.SearchProductUseCase
import com.harrycampaz.searchproducts.utils.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchProductViewModelTest {


    private lateinit var viewModel: SearchProductViewModel

    private val useCase: SearchProductUseCase = mockk()

    @Rule
    @JvmField
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup() {
        viewModel = SearchProductViewModel(useCase)
    }


    @Test
    fun `when queryText is valid THEN show Loading State`() = coroutinesTestRule.runTest {
        // Given
        viewModel.queryText.value = "gasghga"

        val eventList = mutableListOf<SearchProductState>()

        // When


        val job = launch {
            viewModel.searchProductState.collectLatest {
                eventList.add(it)
            }
        }

        viewModel.sendAction(SearchProductViewModel.SearchProductAction.SendRequest)
        // Then

        assertTrue(eventList.first() is SearchProductState.ShowLoading)

        job.cancel()
    }

    @Test
    fun `when queryText is blank then show error input`() = coroutinesTestRule.runTest {
        // Given
        viewModel.queryText.value = ""

        val eventList = mutableListOf<SearchProductState>()

        // When


        val job = launch {
            viewModel.searchProductState.collectLatest {
                eventList.add(it)
            }
        }

        viewModel.sendAction(SearchProductViewModel.SearchProductAction.SendRequest)
        // Then

        assertTrue(eventList.first() is SearchProductState.ErrorInput)

        job.cancel()
    }

    @Test
    fun `validateString should emit ShowLoading and NavigateToProductList when queryText is valid`() = coroutinesTestRule.runTest {
        // Given
        val response = listOf<ProductEntity>()
        coEvery { useCase.searchProduct(any()) } returns Result.success(response)
        viewModel.queryText.value = "search query"

        val eventList = mutableListOf<SearchProductState>()

        // When


        val job = launch {
            viewModel.searchProductState.collectLatest {
                eventList.add(it)
            }
        }

        // When
        viewModel.sendAction(SearchProductViewModel.SearchProductAction.SendRequest)

        // Then
        assert(eventList.first() is SearchProductState.ShowLoading)
        assert(eventList.last() is SearchProductState.NavigateToProductList)

        job.cancel()
    }

    @Test
    fun `validateString WHEN return UnknownException THEN emit ShowLoading and UnknownError State`() = coroutinesTestRule.runTest {
        // Given
        coEvery { useCase.searchProduct(any()) } returns Result.failure(UnknownException(" UnknownError error"))
        viewModel.queryText.value = "search query"

        val eventList = mutableListOf<SearchProductState>()

        // When


        val job = launch {
            viewModel.searchProductState.collectLatest {
                eventList.add(it)
            }
        }

        // When
        viewModel.sendAction(SearchProductViewModel.SearchProductAction.SendRequest)

        // Then
        assert(eventList.first() is SearchProductState.ShowLoading)
        assert(eventList.last() is SearchProductState.UnknownError)

        job.cancel()
    }


    @Test
    fun `validateString WHEN return NetworkException THEN emit ShowLoading and InternetError State`() = coroutinesTestRule.runTest {
        // Given
        coEvery { useCase.searchProduct(any()) } returns Result.failure(NetworkException("Network error"))
        viewModel.queryText.value = "search query"

        val eventList = mutableListOf<SearchProductState>()

        // When


        val job = launch {
            viewModel.searchProductState.collectLatest {
                eventList.add(it)
            }
        }

        // When
        viewModel.sendAction(SearchProductViewModel.SearchProductAction.SendRequest)

        // Then
        assert(eventList.first() is SearchProductState.ShowLoading)
        assert(eventList.last() is SearchProductState.InternetError)

        job.cancel()
    }

    @Test
    fun `validateString WHEN return ServerException THEN emit ShowLoading and ServerError State`() = coroutinesTestRule.runTest {
        // Given
        coEvery { useCase.searchProduct(any()) } returns Result.failure(ServerException(500, "Network error"))
        viewModel.queryText.value = "search query"

        val eventList = mutableListOf<SearchProductState>()

        // When


        val job = launch {
            viewModel.searchProductState.collectLatest {
                eventList.add(it)
            }
        }

        // When
        viewModel.sendAction(SearchProductViewModel.SearchProductAction.SendRequest)

        // Then
        assert(eventList.first() is SearchProductState.ShowLoading)
        assert(eventList.last() is SearchProductState.ServerError)

        job.cancel()
    }

}