package com.harrycampaz.searchproducts.presentation.ui.productList


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harrycampaz.searchproducts.presentation.ui.searchProduct.viewobject.ProductViewObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor() : ViewModel() {


    private val listProductIntent = Channel<ProductListAction>(Channel.BUFFERED)

    private val _productListState: MutableSharedFlow<ProductListState> = MutableSharedFlow()
    val productListState = _productListState.asSharedFlow()


    init {
        handleIntent()
    }

    private fun handleIntent() = viewModelScope.launch {
        listProductIntent.consumeAsFlow().collectLatest { action ->
            when (action) {
                ProductListAction.Idle -> {
                    setupView()
                }
                is ProductListAction.NavigateToDetail -> {
                    _productListState.emit(ProductListState.GoToDetail(action.productViewObject))
                }
                ProductListAction.NavigateBack -> _productListState.emit(ProductListState.GoBack)
            }
        }
    }

    private fun setupView() = viewModelScope.launch {
        _productListState.emit(ProductListState.InitView)
    }

    fun sendAction(action: ProductListAction) = viewModelScope.launch {
        listProductIntent.send(action)
    }

    sealed class ProductListState {
        object InitView : ProductListState()
        data class GoToDetail(val productViewObject: ProductViewObject) : ProductListState()
        object GoBack : ProductListState()
    }

    sealed class ProductListAction {
        object Idle : ProductListAction()
        data class NavigateToDetail(val productViewObject: ProductViewObject) : ProductListAction()
        object NavigateBack : ProductListAction()
    }

}