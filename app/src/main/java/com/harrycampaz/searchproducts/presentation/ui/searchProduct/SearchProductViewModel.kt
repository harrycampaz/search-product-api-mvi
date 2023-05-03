package com.harrycampaz.searchproducts.presentation.ui.searchProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harrycampaz.searchproducts.common.NetworkException
import com.harrycampaz.searchproducts.common.ServerException
import com.harrycampaz.searchproducts.domain.entities.ProductEntity
import com.harrycampaz.searchproducts.domain.usecase.SearchProductUseCase
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
class SearchProductViewModel @Inject constructor(
    private val useCase: SearchProductUseCase
) : ViewModel() {

    var queryText: MutableStateFlow<String> = MutableStateFlow("")


    private val searchProductIntent = Channel<SearchProductAction>(Channel.BUFFERED)

    private val _searchProductState: MutableSharedFlow<SearchProductState> = MutableSharedFlow()

    val searchProductState = _searchProductState.asSharedFlow()

    init {
        handleIntent()
    }

    private fun handleIntent() = viewModelScope.launch {
        searchProductIntent.consumeAsFlow().collectLatest { action ->
            when (action) {
                SearchProductAction.SendRequest -> validateString()
            }
        }
    }

    private fun validateString() = viewModelScope.launch {
        when {
            queryText.value.isBlank() || queryText.value.length < 2 ->
                _searchProductState.emit(SearchProductState.ErrorInput)
            else -> {
                _searchProductState.emit(SearchProductState.ShowLoading)
                requestSearchProduct()
            }
        }
    }

    private fun requestSearchProduct() = viewModelScope.launch {

        useCase.searchProduct(queryText.value).onSuccess { response ->
            handleSuccess(response)

        }.onFailure { error ->
            handleErrors(error)
        }
    }

    private suspend fun handleErrors(error: Throwable) {
        when (error) {
            is NetworkException -> _searchProductState.emit(SearchProductState.InternetError)
            is ServerException -> _searchProductState.emit(SearchProductState.ServerError)
            else -> _searchProductState.emit(SearchProductState.UnknownError)
        }
    }

    private suspend fun handleSuccess(response: List<ProductEntity>) {
        _searchProductState.emit(SearchProductState.HideLoading)
        if (response.isEmpty()) _searchProductState.emit(SearchProductState.EmptyList)
        else _searchProductState.emit(SearchProductState.NavigateToProductList(response))
    }

    fun sendAction(action: SearchProductAction) = viewModelScope.launch {
        searchProductIntent.send(action)
    }

    sealed class SearchProductAction {
        object SendRequest : SearchProductAction()
    }

}

