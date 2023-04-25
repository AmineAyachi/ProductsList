package com.productslist.core.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.newsapp.core.util.Resource
import com.productslist.core.data.repository.Implementation.MockProductRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetReviewTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var getproduct: GetProduct
    val mockRepository = MockProductRepository()

    @Before
    fun setup(){
        getproduct = GetProduct(mockRepository)
    }

    @Test
    fun `getproduct should return, returns success`()  = runBlocking {
        val result  = getproduct.invoke().first()
        assertTrue(result is Resource.Success)
        val products = (result as Resource.Success).data
        if (products != null) {
            assertEquals(products.size, 0)
        }
        mockRepository.setShouldReturnNetworkEroor(true)
        val errorResult = getproduct.invoke().first()

        // Verify that the result is an error
        assertTrue(errorResult is Resource.Error)
        assertEquals((errorResult as Resource.Error).message, "Couldn't reach server, check Your internet connection")
    }
}