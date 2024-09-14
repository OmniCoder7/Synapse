package com.proton.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proton.domain.models.ProductPreview
import com.proton.domain.models.User
import com.proton.domain.useCase.GetProductPreviewsUseCase
import com.proton.domain.useCase.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    getUserUseCase: GetUserUseCase,
    private val getProductPreviewsUseCase: GetProductPreviewsUseCase,
    id: Long,
) : ViewModel() {

    private val _products = MutableStateFlow<List<ProductPreview>>(emptyList())
    val products: StateFlow<List<ProductPreview>> = _products.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)

    private var pageNo = 0
    private val pageSize = 10

    init {
        viewModelScope.launch {
                _user.value = getUserUseCase(id, viewModelScope).await()
        }
    }

    fun getNextProducts() {
        viewModelScope.launch {
            val nextProducts =
                getProductPreviewsUseCase(pageNo, pageSize, coroutineScope = viewModelScope)
            _products.value += nextProducts
            pageNo++
        }
    }

    fun addToCart(productId: Long) {

    }

    fun addToWishList(productId: Long) {

    }
}