package com.harrycampaz.searchproducts.presentation.ui.detailsProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsProductViewModel @Inject constructor(): ViewModel() {

    private val detailsProductIntent = Channel<DetailsProductAction>(Channel.BUFFERED)

    private val _detailsProductState: MutableSharedFlow<DetailsProductState> = MutableSharedFlow()
    val detailsProductState get() = _detailsProductState
    init {
        handleIntent()
    }

    private fun handleIntent() = viewModelScope.launch{
        detailsProductIntent.consumeAsFlow().collectLatest { action ->
            when(action){
                DetailsProductAction.Idle -> {
                    _detailsProductState.emit(DetailsProductState.InitView)
                }
            }
        }
    }

    fun sendAction(action: DetailsProductAction) = viewModelScope.launch {
        detailsProductIntent.send(action)
    }

    sealed class DetailsProductState {
        object InitView : DetailsProductState()
    }

    sealed class DetailsProductAction {
        object Idle : DetailsProductAction()
    }

}