package com.wind.tv.android.ui.detail

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.shared.common_compose.components.ChoiceChipContent
import com.shared.common_compose.components.CollapsableAppBar
import com.shared.common_compose.components.ColumnSpacer
import com.shared.common_compose.components.ExpandingText
import com.shared.common_compose.components.ExtendedFab
import com.shared.common_compose.components.FullScreenLoading
import com.shared.common_compose.components.KenBurnsViewImage
import com.shared.common_compose.components.LoadingItem
import com.shared.common_compose.components.RowSpacer
import com.shared.common_compose.theme.TvManiacTheme
import com.shared.common_compose.theme.backgroundGradient
import com.shared.common_compose.util.copy
import com.shared.myapplication.model.TvGenre
import com.shared.myapplication.model.TvSeason
import com.shared.myapplication.model.TvShow
import com.shared.myapplication.viewmodel.detail.ShowDetailAction
import com.shared.myapplication.viewmodel.detail.ShowDetailEffect
import com.shared.myapplication.viewmodel.detail.ShowDetailViewState
import com.shared.myapplication.viewmodel.detail.ShowDetailsViewModel
import com.shared.myapplication.viewmodel.home.TvShowUI
import com.wind.tv.android.R
import com.wind.tv.android.ui.showdetails.EpisodesReleaseContent
import com.wind.tv.android.ui.showdetails.SimilarShowsShowsContent
import com.wind.tv.android.util.detailUiState
import com.wind.tv.android.util.getExtra
import kotlinx.collections.immutable.ImmutableList
import org.koin.androidx.compose.getViewModel

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

private val HeaderHeight = 550.dp

@Composable
fun DetailScreen(
    navigateUp: () -> Unit,
    onShowClicked: (String) -> Unit,
    onSeasonClicked: (String, String) -> Unit = { _, _ -> },
    onEpisodeClicked: (Long, Long) -> Unit = { _, _ -> }
) {

    val viewModel = getViewModel<ShowDetailsViewModel>()

    val viewState by viewModel.state.collectAsState()

    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        val tvShow = context.findActivity()!!.intent!!.getExtra<TvShow>()
        viewModel.dispatch(ShowDetailAction.SetTvShow(tvShow))
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect {
            when (it) {
                is ShowDetailEffect.WatchlistError ->
                    scaffoldState.snackbarHostState
                        .showSnackbar(it.errorMessage)
                is ShowDetailEffect.ShowDetailsError ->
                    scaffoldState.snackbarHostState
                        .showSnackbar(it.errorMessage)
            }
        }
    }

    val listState = rememberLazyListState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ShowTopBar(
                listState = listState,
                title = (viewState as? ShowDetailViewState.Success)?.tvShow?.show?.title.orEmpty(),
                onNavUpClick = navigateUp
            )
        },
        content = { contentPadding ->
            when (val state = viewState) {
                ShowDetailViewState.InProgress -> FullScreenLoading()
                is ShowDetailViewState.Success -> {
                    TvShowDetailsScrollingContent(
                        detailUiState = state,
                        listState = listState,
                        contentPadding = contentPadding,
                        onSeasonClicked = onSeasonClicked,
                        onEpisodeClicked = onEpisodeClicked,
                        onShowClicked = onShowClicked,
                        onUpdateFavoriteClicked = {
                            viewModel.dispatch(
                                ShowDetailAction.UpdateFavorite(
                                    it
                                )
                            )
                        },
                        onBookmarkEpClicked = {
                            viewModel.dispatch(
                                ShowDetailAction.BookmarkEpisode(
                                    it
                                )
                            )
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun ShowTopBar(
    listState: LazyListState,
    title: String,
    onNavUpClick: () -> Unit
) {
    var appBarHeight by remember { mutableStateOf(0) }
    val showAppBarBackground by remember {
        derivedStateOf {
            val visibleItemsInfo = listState.layoutInfo.visibleItemsInfo
            when {
                visibleItemsInfo.isEmpty() -> false
                appBarHeight <= 0 -> false
                else -> {
                    val firstVisibleItem = visibleItemsInfo[0]
                    when {
                        // If the first visible item is > 0, we want to show the app bar background
                        firstVisibleItem.index > 0 -> true
                        // If the first item is visible, only show the app bar background once the only
                        // remaining part of the item is <= the app bar
                        else -> firstVisibleItem.size + firstVisibleItem.offset - 5 <= appBarHeight
                    }
                }
            }
        }
    }

    CollapsableAppBar(
        title = title,
        showAppBarBackground = showAppBarBackground,
        onNavIconPressed = onNavUpClick,
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged {
                appBarHeight = it.height
            }
    )
}

@Composable
private fun TvShowDetailsScrollingContent(
    detailUiState: ShowDetailViewState.Success,
    listState: LazyListState,
    contentPadding: PaddingValues,
    onUpdateFavoriteClicked: (Boolean) -> Unit = {},
    onSeasonClicked: (String, String) -> Unit = { _, _ -> },
    onEpisodeClicked: (Long, Long) -> Unit = { _, _ -> },
    onBookmarkEpClicked: (String) -> Unit = { },
    onShowClicked: (String) -> Unit = {}
) {

    LazyColumn(
        state = listState,
        contentPadding = contentPadding.copy(copyTop = false),
    ) {

        item(key = "header") {
            HeaderViewContent(
                detailUiState = detailUiState,
                listState = listState,
                onUpdateFavoriteClicked = onUpdateFavoriteClicked
            )
        }

        item {
            MoreBodyContent(
                detailUiState = detailUiState,
                onSeasonClicked = onSeasonClicked,
                onEpisodeClicked = onEpisodeClicked,
                onBookmarkEpClicked = onBookmarkEpClicked,
                onShowClicked = onShowClicked
            )
        }

        item(key = "offset nav bar") {
            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}

@Composable
private fun HeaderViewContent(
    detailUiState: ShowDetailViewState.Success,
    listState: LazyListState,
    onUpdateFavoriteClicked: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(HeaderHeight)
            .clipToBounds()
            .offset {
                IntOffset(
                    x = 0,
                    y = if (listState.firstVisibleItemIndex == 0) {
                        listState.firstVisibleItemScrollOffset / 2
                    } else 0
                )
            }
    ) {
        HeaderImage(
            backdropImageUrl = detailUiState.tvShow.show.backdropImageUrl
        )

        Body(
            tvShowUI = detailUiState.tvShow,
            genreUIS = detailUiState.genreUIList,
            onUpdateFavoriteClicked = onUpdateFavoriteClicked
        )
    }
}

@Composable
private fun HeaderImage(backdropImageUrl: String) {
    KenBurnsViewImage(
        imageUrl = backdropImageUrl,
        modifier = Modifier
            .fillMaxWidth()
            .height(HeaderHeight)
    )
}

@Composable
private fun Body(
    tvShowUI: TvShowUI,
    genreUIS: ImmutableList<TvGenre>,
    onUpdateFavoriteClicked: (Boolean) -> Unit
) {
    val surfaceGradient = backgroundGradient().reversed()
    val tvShow = tvShowUI.show

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(HeaderHeight)
            .background(Brush.verticalGradient(surfaceGradient))
            .padding(horizontal = 16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {

            Text(
                text = tvShow.title,
                style = MaterialTheme.typography.h4,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            ColumnSpacer(8)

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                ExpandingText(
                    text = tvShow.overview,
                )
            }

            ColumnSpacer(8)

            TvShowMetadata(
                tvShowUI = tvShowUI,
                genreUIList = genreUIS,
                onUpdateFavoriteClicked = onUpdateFavoriteClicked,
            )
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun TvShowMetadata(
    tvShowUI: TvShowUI,
    genreUIList: ImmutableList<TvGenre>,
    onUpdateFavoriteClicked: (Boolean) -> Unit,
    onWatchTrailerClicked: () -> Unit = {},
) {
    val resources = LocalContext.current.resources
    val tvShow = tvShowUI.show

    val divider = buildAnnotatedString {
        val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
            color = MaterialTheme.colors.secondary
        )
        withStyle(tagStyle) {
            append("  •  ")
        }
    }
    val text = buildAnnotatedString {
        val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
            color = MaterialTheme.colors.secondary,
            background = MaterialTheme.colors.secondary.copy(alpha = 0.08f)
        )

        AnimatedVisibility(visible = !tvShow.status.isNullOrBlank()) {
            withStyle(tagStyle) {
                append(" ")
                append(tvShow.status!!)
                append(" ")
            }
            append(divider)
        }
        append(tvShow.year)

        AnimatedVisibility(visible = tvShow.numberOfSeasons != null) {
            tvShow.numberOfSeasons?.let {
                append(divider)
                append(resources.getQuantityString(R.plurals.season_count, it.toInt(), it.toInt()))
            }
        }

        append(divider)
        append(tvShow.language.uppercase())
        append(divider)
        append("${tvShow.averageVotes}")
        append(divider)
    }

    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.fillMaxWidth()
        )
    }

    ColumnSpacer(8)

    GenreText(genreUIList)

    ColumnSpacer(8)

    ShowDetailButtons(
        isFollowing = tvShowUI.following,
        onUpdateFavoriteClicked = onUpdateFavoriteClicked,
        onWatchTrailerClicked = onWatchTrailerClicked
    )
}

@Composable
private fun GenreText(
    genreUIList: ImmutableList<TvGenre>
) {

    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(genreUIList, key = {
            it.id
        }) { item ->

            TextButton(
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colors.onBackground,
                    backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.08f)
                ),
                onClick = {}
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.body2,
                )
            }

            RowSpacer(4)

        }
    }
}

@Composable
fun ShowDetailButtons(
    isFollowing: Boolean,
    onUpdateFavoriteClicked: (Boolean) -> Unit,
    onWatchTrailerClicked: () -> Unit = {},
) {

    Row {

        ExtendedFab(
            painter = painterResource(id = R.drawable.ic_trailer_24),
            text = stringResource(id = R.string.btn_trailer),
            onClick = { onWatchTrailerClicked() }
        )

        RowSpacer(value = 8)

        val buttonText = if (isFollowing)
            stringResource(id = R.string.unfollow)
        else stringResource(id = R.string.following)

        val imageVector = if (isFollowing)
            painterResource(id = R.drawable.ic_baseline_check_box_24)
        else painterResource(id = R.drawable.ic_baseline_add_box_24)

        ExtendedFab(
            painter = imageVector,
            text = buttonText,
            onClick = {
                onUpdateFavoriteClicked(isFollowing)
            }
        )
    }
}

@Composable
private fun MoreBodyContent(
    detailUiState: ShowDetailViewState.Success,
    onSeasonClicked: (String, String) -> Unit,
    onBookmarkEpClicked: (String) -> Unit,
    onEpisodeClicked: (Long, Long) -> Unit,
    onShowClicked: (String) -> Unit
) {
    LoadingItem(
        isLoading = detailUiState.tvSeasonUiModels.isEmpty()
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {

            ColumnSpacer(16)

            if (detailUiState.tvSeasonUiModels.isNotEmpty()) {
                Text(
                    text = stringResource(id = R.string.title_seasons),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                ShowSeasonsTabs(
                    seasonUiModelList = detailUiState.tvSeasonUiModels,
                    onSeasonClicked = onSeasonClicked,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            EpisodesReleaseContent(
                episodeList = detailUiState.lastAirEpList,
                onEpisodeClicked = onEpisodeClicked,
                onBookmarkEpClicked = onBookmarkEpClicked
            )

            SimilarShowsShowsContent(
                similarShows = detailUiState.similarShowList,
                onShowClicked = onShowClicked
            )
        }

    }
}

@Composable
private fun ShowSeasonsTabs(
    seasonUiModelList: ImmutableList<TvSeason>,
    modifier: Modifier,
    onSeasonClicked: (String, String) -> Unit
) {
    val selectedIndex by remember { mutableStateOf(0) }

    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        divider = {}, /* Disable the built-in divider */
        indicator = {},
        edgePadding = 0.dp,
        backgroundColor = Color.Transparent,
        modifier = modifier.fillMaxWidth()
    ) {
        seasonUiModelList.forEach { season ->
            Tab(
                selected = true,
                onClick = { onSeasonClicked(season.tvShowId, season.name) }
            ) {
                ChoiceChipContent(
                    text = season.name,
                    selected = true,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TvShowDetailsScrollingPreview() {
    TvManiacTheme {
        Surface {
            TvShowDetailsScrollingContent(
                detailUiState = detailUiState,
                listState = LazyListState(),
                contentPadding = PaddingValues(),
                onUpdateFavoriteClicked = {},
                onShowClicked = {},
                onSeasonClicked = { _, _ -> }
            )
        }
    }
}
