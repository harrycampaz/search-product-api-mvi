package com.harrycampaz.searchproducts.presentation.ui.productList

import com.harrycampaz.searchproducts.data.models.toListProductEntity
import com.harrycampaz.searchproducts.presentation.ui.searchProduct.viewobject.toViewObject
import com.harrycampaz.searchproducts.utils.CoroutinesTestRule
import com.harrycampaz.searchproducts.utils.getFakeProductResponse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {

    private lateinit var viewModel: ProductListViewModel

    @Rule
    @JvmField
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup(){
        viewModel = ProductListViewModel()
    }

    @Test
    fun `WHEN sent Idle Action THEN emit InitView State to the View`() = coroutinesTestRule.runTest {

        val eventList = mutableListOf<ProductListViewModel.ProductListState>()

        val job = launch {
            viewModel.productListState.collectLatest {
                eventList.add(it)
            }
        }

        viewModel.sendAction(ProductListViewModel.ProductListAction.Idle)

        assertTrue(eventList.first() is ProductListViewModel.ProductListState.InitView)

        job.cancel()
    }

    @Test
    fun `WHEN sent NavigateToDetail Action THEN emit GoToDetail State to the View`() = coroutinesTestRule.runTest {

        val productItem = getFakeProductResponse().toListProductEntity().first().toViewObject()

        val eventList = mutableListOf<ProductListViewModel.ProductListState>()

        val job = launch {
            viewModel.productListState.collectLatest {
                eventList.add(it)
            }
        }

        viewModel.sendAction(ProductListViewModel.ProductListAction.NavigateToDetail(productItem))

        assertTrue(eventList.first() is ProductListViewModel.ProductListState.GoToDetail)

        job.cancel()
    }

}

