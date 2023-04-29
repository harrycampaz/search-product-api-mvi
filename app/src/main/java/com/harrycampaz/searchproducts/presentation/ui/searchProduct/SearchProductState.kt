package com.harrycampaz.searchproducts.presentation.ui.searchProduct

import com.harrycampaz.searchproducts.domain.entities.ProductEntity

sealed class SearchProductState {
    object ShowLoading : SearchProductState()
    object HideLoading : SearchProductState()
    object ErrorInput: SearchProductState()
    data class NavigateToProductList(val productList: List<ProductEntity>) : SearchProductState()
    object InternetError : SearchProductState()
    object ServerError : SearchProductState()
    object UnknownError : SearchProductState()
}