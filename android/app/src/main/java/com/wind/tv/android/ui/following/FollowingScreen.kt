package com.wind.tv.android.ui.following

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.shared.common_compose.components.EmptyContentView
import com.shared.common_compose.components.FullScreenLoading
import com.shared.common_compose.components.LazyGridItems
import com.shared.common_compose.components.NetworkImageComposable
import com.shared.common_compose.components.SwipeDismissSnackbar
import com.shared.myapplication.viewmodel.following.FollowingViewModel
import com.shared.myapplication.viewmodel.following.WatchlistEffect
import com.shared.myapplication.viewmodel.following.WatchlistState
import com.wind.tv.android.R
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FollowingScreen(
    openShowDetails: (showId: String) -> Unit
) {
    val viewModel = getViewModel<FollowingViewModel>()

    val watchlistViewState by viewModel.state.collectAsState()

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is WatchlistEffect.Error -> scaffoldState.snackbarHostState.showSnackbar(it.message)
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .statusBarsPadding()
            .padding(bottom = 64.dp),
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
        content = {
            WatchlistContent(
                viewState = watchlistViewState,
                onItemClicked = { tvShowId ->
                    openShowDetails(tvShowId)
                }
            )
        }
    )
}


@Composable
private fun WatchlistContent(
    viewState: WatchlistState,
    onItemClicked: (String) -> Unit,
) {

    when (viewState) {
        WatchlistState.InProgress -> FullScreenLoading()
        is WatchlistState.Success -> {
            val listState = rememberLazyListState()

            if (viewState.data.isEmpty())
                EmptyContentView(
                    painter = painterResource(id = R.drawable.ic_watchlist_empty),
                    message = stringResource(id = R.string.msg_empty_favorites)
                )
            else
                LazyGridItems(
                    listState = listState,
                    items = viewState.data
                ) { show ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize()
                    ) {
                        Card(
                            elevation = 4.dp,
                            modifier = Modifier.clickable { onItemClicked(show.id) },
                            shape = MaterialTheme.shapes.medium
                        ) {
                            NetworkImageComposable(
                                imageUrl = show.posterImageUrl,
                                contentDescription = stringResource(
                                    R.string.cd_show_poster,
                                    show.title
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(2 / 3f)
                            )
                        }
                    }
                }
        }
    }
}
