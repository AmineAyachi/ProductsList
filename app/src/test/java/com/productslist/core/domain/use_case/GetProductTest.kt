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

class GetProductTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var getReview: GetReview
    val mockRepository = MockProductRepository()

    @Before
    fun setup(){
        getReview = GetReview(mockRepository)
    }

    @Test
    fun `getReview should return, returns success`()  = runBlocking {
        val result  = getReview.invoke().first()
        assertTrue(result is Resource.Success)
        val reviews = (result as Resource.Success).data
        if (reviews != null) {
            assertEquals(reviews.size, 0)
        }
        mockRepository.setShouldReturnNetworkEroor(true)
        val errorResult = getReview.invoke().first()

        // Verify that the result is an error
        assertTrue(errorResult is Resource.Error)
        assertEquals((errorResult as Resource.Error).message, "Couldn't reach server, check Your internet connection")
    }
}