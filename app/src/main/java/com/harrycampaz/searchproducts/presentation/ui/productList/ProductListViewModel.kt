package com.harrycampaz.searchproducts.presentation.ui.productList

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harrycampaz.searchproducts.data.repository.ProductRepository
import com.harrycampaz.searchproducts.presentation.ui.searchProduct.viewobject.ListProductViewObject
import com.harrycampaz.searchproducts.presentation.ui.searchProduct.viewobject.ProductViewObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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

    private fun handleIntent() = viewModelScope.launch{
        listProductIntent.consumeAsFlow().collectLatest { action ->
            when(action){
                ProductListAction.Idle -> {
                   setupView()
                }
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
        object GoToDetail : ProductListState()
    }

    sealed class ProductListAction {
       object Idle: ProductListAction()
    }

}