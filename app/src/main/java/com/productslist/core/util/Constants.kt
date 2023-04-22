package com.newsapp.core.util

import com.newsapp.core.domain.model.Article
import java.util.Locale

class Constants {

    companion object {
        const val API_KEY = "5cebc833f78f45eabaa6b76e29890359" // Api keys should not exposed as alternative we can implement some auth to get it from server sorry that i did that to have some time
        const val BASE_URL = "https://newsapi.org"
        var selectedItem:Article?= null
        var country = Locale.getDefault().getCountry()
    }
    }