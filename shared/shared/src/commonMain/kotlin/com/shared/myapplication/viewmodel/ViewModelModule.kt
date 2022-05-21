package com.shared.myapplication.viewmodel

import com.shared.myapplication.viewmodel.following.FollowingViewModel
import com.shared.myapplication.viewmodel.home.DiscoverViewModel
import com.shared.util.viewModelDefinition
import org.koin.dsl.module

val viewmodelModule = module {
    viewModelDefinition { DiscoverViewModel() }
    viewModelDefinition { FollowingViewModel() }
}
