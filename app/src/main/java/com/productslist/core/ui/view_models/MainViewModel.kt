package com.newsapp.core.activities.view_models

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.core.util.Resource
import com.productslist.core.data.remote.dto.responses.ProductsReviews
import com.productslist.core.domain.model.Product
import com.productslist.core.domain.model.Review
import com.productslist.core.domain.use_case.GetProduct
import com.productslist.core.domain.use_case.GetReview
import com.productslist.core.ui.view_models.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProduct: GetProduct,
    private val getReview: GetReview
    )  : ViewModel(){

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private val _mainState = mutableStateOf(MainState())
    var mainState: State<MainState> = _mainState

    init {
        products()
    }


    private var fetchJob: Job? = null
    fun products() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            getProduct().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _mainState.value =  _mainState.value.copy(
                            products  =  result.data ?: emptyList<Product>(),
                            isProductsloading = false,
                            productError=false
                        )
                        reviews()
                        _eventFlow.emit(UIEvent.UpdateRceyclerView())
                    }
                    is Resource.Error -> {
                        _mainState.value =  _mainState.value.copy(
                            isProductsloading = false,
                            productError=true
                        )
                        _eventFlow.emit(UIEvent.ShowToast(result.message ?: "Unknown error"))
                        _eventFlow.emit(UIEvent.ShowRefreshBtn())
                    }
                    is Resource.Loading -> {
                        _mainState.value =  _mainState.value.copy(
                            isProductsloading = true,
                            productError=false
                        )
                        _eventFlow.emit(UIEvent.ShowLoadingBar())
                    }
                }
            }.launchIn(this)
        }
    }

    fun reviews() {
        viewModelScope.launch {
            getReview().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _mainState.value =  _mainState.value.copy(
                            reviews  =  result.data ?: emptyList<ProductsReviews>(),
                            isReviewsloading = false,
                            reviewError =false
                        )
                        val updatedProducts = withContext(Dispatchers.IO) {
                            fillProductsWithReviews(_mainState.value.products, _mainState.value.reviews)
                        }

                        _mainState.value = _mainState.value.copy(
                            products = updatedProducts,
                        )
                        _eventFlow.emit(UIEvent.UpdateRceyclerView())

                    }
                    is Resource.Error -> {
                        _mainState.value =  _mainState.value.copy(
                            isReviewsloading = false,
                            reviewError=true
                        )
                        _eventFlow.emit(UIEvent.ShowToast(result.message ?: "Unknown error"))
                    }
                    is Resource.Loading -> {
                        _mainState.value =  _mainState.value.copy(
                            isReviewsloading = true,
                            reviewError=false
                        )
                        _eventFlow.emit(UIEvent.ShowLoadingBar())
                    }
                }
            }.launchIn(this)
        }
    }

    suspend fun fillProductsWithReviews(products: List<Product>, productsReviews: List<ProductsReviews>): List<Product> {
        return products.map { product ->
            val productReviews = productsReviews.find { it.product_id == product.product_id }

            if (productReviews != null ) {
                if(productReviews.hide != null ){
//                    if(!productReviews.hide){
                        product.copy(reviews = productReviews.reviews)
//                    }else {
//                        product
//                    }

                }else {
                    product.copy(reviews = productReviews.reviews)
                }

            } else {
                product
            }
        }
    }


    sealed class UIEvent {
        data class ShowToast(val message: String) : UIEvent()
        class UpdateRceyclerView () : UIEvent()
        class ShowLoadingBar () : UIEvent()
        class ShowRefreshBtn () : UIEvent()
    }


}