package com.proton.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.proton.domain.models.ProductPreview
import com.proton.domain.service.ProductService
import com.proton.home.UiAction.Search
import com.proton.home.UiModel.SeparatorItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val productService: ProductService,
) : ViewModel() {

    val state: StateFlow<UiState>
    val pagingDataFlow: Flow<PagingData<UiModel>>
    val accept: (UiAction) -> Unit

    init {
        val initialQuery: String = savedStateHandle[LAST_SEARCH_QUERY] ?: DEFAULT_QUERY
        val lastQueryScrolled: String = savedStateHandle[LAST_QUERY_SCROLLED] ?: DEFAULT_QUERY
        val actionStateFlow = MutableSharedFlow<UiAction>()
        val searches = actionStateFlow
            .filterIsInstance<Search>()
            .distinctUntilChanged()
            .onStart { emit(Search(query = initialQuery)) }
        val queriesScrolled = actionStateFlow
            .filterIsInstance<UiAction.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(UiAction.Scroll(currentQuery = lastQueryScrolled)) }

        pagingDataFlow = searches
            .flatMapLatest { searchProducts(queryString = it.query) }
            .cachedIn(viewModelScope)

        state = combine(
            searches,
            queriesScrolled,
            ::Pair
        ).map { (search, scroll) ->
            UiState(
                query = search.query,
                lastQueryScrolled = scroll.currentQuery,
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = UiState()
            )

        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
    }

    fun addToCart(productId: Long) {
        viewModelScope.launch { productService.addToCart(productId) }
    }

    fun addToWishList(productId: Long) {
        viewModelScope.launch { productService.addToWishList(productId) }
    }

    private fun searchProducts(queryString: String): Flow<PagingData<UiModel>> =
        productService.getProduct(queryString)
            .map { pagingData -> pagingData.map { UiModel.ProductPreviewItem(it) } }
            .map {
                it.insertSeparators { before, after ->
                    if (after == null) {
                        return@insertSeparators null
                    }

                    if (before == null) {
                        return@insertSeparators SeparatorItem("${after.roundedStarCount}0.000+ stars")
                    }
                    if (before.roundedStarCount > after.roundedStarCount) {
                        if (after.roundedStarCount >= 1) {
                            SeparatorItem("${after.roundedStarCount}0.000+ stars")
                        } else {
                            SeparatorItem("< 10.000+ stars")
                        }
                    } else {
                        null
                    }
                }
            }

    override fun onCleared() {
        savedStateHandle[LAST_SEARCH_QUERY] =
            state.value.query
        savedStateHandle[LAST_QUERY_SCROLLED] =
            state.value.lastQueryScrolled
        super.onCleared()
    }
}

sealed class UiAction {
    data class Search(val query: String) : UiAction()
    data class Scroll(val currentQuery: String) : UiAction()
}

data class UiState(
    val query: String = DEFAULT_QUERY,
    val lastQueryScrolled: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false,
)

sealed class UiModel {
    data class ProductPreviewItem(val productPreview: ProductPreview) : UiModel()
    data class SeparatorItem(val description: String) : UiModel()
}

private val UiModel.ProductPreviewItem.roundedStarCount: Int
    get() = (this.productPreview.rating / 10_000).toInt()

private const val LAST_QUERY_SCROLLED: String = "last_query_scrolled"
private const val LAST_SEARCH_QUERY: String = "last_search_query"
private const val DEFAULT_QUERY = "Synapse"