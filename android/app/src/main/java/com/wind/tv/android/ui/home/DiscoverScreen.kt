package com.wind.tv.android.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import co.touchlab.kermit.Logger
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.shared.common_compose.components.BoxTextItems
import com.shared.common_compose.components.ColumnSpacer
import com.shared.common_compose.components.FullScreenLoading
import com.shared.common_compose.components.NetworkImageComposable
import com.shared.common_compose.components.SwipeDismissSnackbar
import com.shared.common_compose.components.TvShowCard
import com.shared.common_compose.theme.contrastAgainst
import com.shared.common_compose.theme.grey900
import com.shared.common_compose.util.DominantColorState
import com.shared.common_compose.util.DynamicThemePrimaryColorsFromImage
import com.shared.common_compose.util.rememberDominantColorState
import com.shared.common_compose.util.verticalGradientScrim
import com.shared.myapplication.model.ShowCategory
import com.shared.myapplication.model.TvShow
import com.shared.myapplication.viewmodel.home.DiscoverShowEffect
import com.shared.myapplication.viewmodel.home.DiscoverShowResult
import com.shared.myapplication.viewmodel.home.DiscoverShowState
import com.shared.myapplication.viewmodel.home.DiscoverViewModel
import com.wind.tv.android.R
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlin.math.absoluteValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel

/**
 * This is the minimum amount of calculated contrast for a color to be used on top of the
 * surface color. These values are defined within the WCAG AA guidelines, and we use a value of
 * 3:1 which is the minimum for user-interface components.
 */
private const val MinContrastOfPrimaryVsSurface = 3f

@Composable
fun DiscoverScreen(
    openShowDetails: (showId: Long) -> Unit,
    moreClicked: (showType: Int) -> Unit,
) {
    val viewModel = getViewModel<DiscoverViewModel>()

    val scaffoldState = rememberScaffoldState()

    val discoverViewState = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest {
            when (it) {
                is DiscoverShowEffect.Error -> scaffoldState.snackbarHostState.showSnackbar(it.message)
            }
        }
    }

    DiscoverShows(
        scaffoldState = scaffoldState,
        discoverViewState = discoverViewState.value,
        openShowDetails = openShowDetails,
        moreClicked = moreClicked
    )
}

@Composable
private fun DiscoverShows(
    scaffoldState: ScaffoldState,
    discoverViewState: DiscoverShowState,
    openShowDetails: (showId: Long) -> Unit,
    moreClicked: (showType: Int) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { snackBarHostState ->
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { snackBarData ->
                    SwipeDismissSnackbar(
                        data = snackBarData,
                        onDismiss = { }
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 64.dp)
                    .fillMaxWidth()
            )
        },
    ) { paddingValues ->
        Logger.d("calculate pad top ${paddingValues.calculateTopPadding()} bot ${paddingValues.calculateBottomPadding()}")
        when (discoverViewState) {
            DiscoverShowState.InProgress -> FullScreenLoading()
            is DiscoverShowState.Success -> {
                LazyColumn() {
                    item {
                        FeaturedItems(
                            showData = discoverViewState.data.featuredShows,
                            onItemClicked = { openShowDetails(it) }
                        )
                    }

                    item {
                        DisplayShowData(
                            category = discoverViewState.data.trendingShows.category,
                            tvShows = discoverViewState.data.trendingShows.tvShows,
                            onItemClicked = { openShowDetails(it) },
                            moreClicked = { moreClicked(it) }
                        )
                    }

                    item {
                        DisplayShowData(
                            category = discoverViewState.data.popularShows.category,
                            tvShows = discoverViewState.data.popularShows.tvShows,
                            onItemClicked = { openShowDetails(it) },
                            moreClicked = { moreClicked(it) }
                        )
                    }

                    item {
                        DisplayShowData(
                            category = discoverViewState.data.topRatedShows.category,
                            tvShows = discoverViewState.data.topRatedShows.tvShows,
                            onItemClicked = { openShowDetails(it) },
                            moreClicked = { moreClicked(it) }
                        )
                    }

                    item(key = "offset nav bar") {
                        Spacer(modifier = Modifier.navigationBarsPadding())
                    }
                }
            }
        }


    }
}

@Composable
fun FeaturedItems(
    showData: DiscoverShowResult.DiscoverShowsData,
    onItemClicked: (Long) -> Unit,
) {

    val surfaceColor = grey900
    val dominantColorState = rememberDominantColorState { color ->
        // We want a color which has sufficient contrast against the surface color
        color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
    }
    val pagerState = rememberPagerState()

    DynamicThemePrimaryColorsFromImage(dominantColorState) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalGradientScrim(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.38f),
                    startYPercentage = 1f,
                    endYPercentage = 0f
                )
                .statusBarsPadding()
        ) {

            ColumnSpacer(value = 24)

            FeaturedHorizontalPager(
                list = showData.tvShows,
                pagerState = pagerState,
                dominantColorState = dominantColorState,
                onClick = { onItemClicked(it) }
            )

            if (showData.tvShows.isNotEmpty())
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                )
        }
    }

    ColumnSpacer(value = 16)
}

@Composable
fun FeaturedHorizontalPager(
    list: ImmutableList<TvShow>,
    pagerState: PagerState,
    dominantColorState: DominantColorState,
    onClick: (Long) -> Unit
) {

    val selectedImageUrl = list.getOrNull(pagerState.currentPage)
        ?.posterImageUrl

    LaunchedEffect(selectedImageUrl) {
        if (selectedImageUrl != null) {
            dominantColorState.updateColorsFromImageUrl(selectedImageUrl)
        } else {
            dominantColorState.reset()
        }
    }

    LaunchedEffect(list) {
        if (list.size >= 4) {
            pagerState.scrollToPage(2)
        }
    }

    HorizontalPager(
        count = list.size,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 45.dp),
        modifier = Modifier
            .fillMaxSize()
    ) { pageNumber ->

        Card(
            Modifier
                .clickable { onClick(list[pageNumber].id) }
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(pageNumber).absoluteValue

                    // We animate the scaleX + scaleY, between 85% and 100%
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .fillMaxWidth()
                .aspectRatio(0.7f)

        ) {
            Box {
                NetworkImageComposable(
                    imageUrl = list[pageNumber].posterImageUrl,
                    contentDescription = stringResource(
                        R.string.cd_show_poster,
                        list[pageNumber].title
                    ),
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .offset {
                            val pageOffset =
                                this@HorizontalPager.calculateCurrentOffsetForPage(pageNumber)
                            // Then use it as a multiplier to apply an offset
                            IntOffset(
                                x = (36.dp * pageOffset).roundToPx(),
                                y = 0
                            )
                        }
                )
            }
        }
    }
}

@Composable
private fun DisplayShowData(
    category: ShowCategory,
    tvShows: ImmutableList<TvShow>,
    onItemClicked: (Long) -> Unit,
    moreClicked: (Int) -> Unit,
) {

    AnimatedVisibility(visible = tvShows.isNotEmpty()) {
        Column {
            BoxTextItems(
                title = category.title,
                moreString = stringResource(id = R.string.str_more),
                onMoreClicked = { moreClicked(category.type) }
            )

            val lazyListState = rememberLazyListState()

            LazyRow(
                state = lazyListState,
                flingBehavior = rememberSnapperFlingBehavior(lazyListState),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    end = 12.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
            ) {
                itemsIndexed(items = tvShows, key = { _, tvShow -> tvShow.id }) { _, tvShow ->
                    TvShowCard(
                        posterImageUrl = tvShow.posterImageUrl,
                        title = tvShow.title,
                        modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                    ) {
                        onItemClicked(tvShow.id)
                    }
                }
            }

            ColumnSpacer(value = 8)
        }
    }
}
