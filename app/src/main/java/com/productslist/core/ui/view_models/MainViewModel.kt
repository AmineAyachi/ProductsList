package com.newsapp.core.activities.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
      //  private @ApplicationContext val context: Context, // memory leak
    )  : ViewModel(){
    init {

    }
}