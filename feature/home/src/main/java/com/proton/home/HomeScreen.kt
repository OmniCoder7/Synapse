package com.proton.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems
import com.proton.domain.models.ProductPreview
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    addToCart: (Long) -> Unit,
    addToWishList: (Long) -> Unit
) {
    val viewmodel = koinViewModel<HomeViewModel>()
    val products = viewmodel.pagingDataFlow.collectAsLazyPagingItems()
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val lazyGridState = rememberLazyGridState()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = lazyGridState,
        ) {
            items(products.itemCount) {
                val productPreview = products[it]
                if (productPreview != null) {
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    productPreview: ProductPreview,
    addToCart: (Long) -> Unit,
    addToWishList: (Long) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = productPreview.productName)
        Row {
            Text(
                text = productPreview.price.toString(),
                textDecoration = if (productPreview.discount > 0) TextDecoration.LineThrough else TextDecoration.None
            )
            if (productPreview.discount > 0)
                Text(text = productPreview.price.times(productPreview.discount.div(100)).toString())
            Text(text = productPreview.rating.toString())
        }

        Button(onClick = { addToCart.invoke(productPreview.productId) }) {
            Text(stringResource(R.string.add_to_cart))
        }
        Button(onClick = { addToWishList.invoke(productPreview.productId) }) {
            Text(stringResource(R.string.add_to_wishlist))
        }
    }
}

@Preview
@Composable
private fun HomeScreen_Preview() {
    HomeScreen(
        addToCart = {},
        addToWishList = {}
    )
}