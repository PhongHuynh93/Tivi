package com.shared.myapplication.data.feature.showDetail

import com.thomaskioko.tvmaniac.datasource.cache.SelectShows

interface ShowCategoryCache {

    fun getShowsByCategoryID(id: Int): List<SelectShows>
}
