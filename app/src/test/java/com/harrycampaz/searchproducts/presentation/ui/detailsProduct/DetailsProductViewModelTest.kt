package com.harrycampaz.searchproducts.presentation.ui.detailsProduct

import com.harrycampaz.searchproducts.utils.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class DetailsProductViewModelTest {
    private lateinit var viewModel: DetailsProductViewModel

    @Rule
    @JvmField
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup() {
        viewModel = DetailsProductViewModel()
    }

    @Test
    fun `WHEN sent NavigateBack Action THEN emit GoBack State to the View`() =
        coroutinesTestRule.runTest {

            val eventList = mutableListOf<DetailsProductViewModel.DetailsProductState>()

            val job = launch {
                viewModel.detailsProductState.collectLatest {
                    eventList.add(it)
                }
            }

            viewModel.sendAction(DetailsProductViewModel.DetailsProductAction.NavigateBack)

            assert(eventList.first() is DetailsProductViewModel.DetailsProductState.GoBack)
            job.cancel()

        }

}
