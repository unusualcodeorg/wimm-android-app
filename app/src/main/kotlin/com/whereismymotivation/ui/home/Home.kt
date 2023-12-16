package com.whereismymotivation.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.whereismymotivation.R
import com.whereismymotivation.ui.feed.Feed
import com.whereismymotivation.ui.mentors.Mentors
import com.whereismymotivation.ui.mybox.MyBox
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.profile.Profile
import com.whereismymotivation.ui.profile.ProfileViewModel
import com.whereismymotivation.ui.search.Search

fun NavGraphBuilder.home(
    modifier: Modifier = Modifier
) {
    composable(Destination.Home.Feed.route) {
        Feed(modifier)
    }
    composable(Destination.Home.Mentors.route) {
        Mentors(modifier)
    }
    composable(Destination.Home.Search.route) {
        Search(modifier)
    }
    composable(Destination.Home.Profile.route) {
        val viewModel: ProfileViewModel = hiltViewModel()
        Profile(modifier, viewModel)
    }

    composable(Destination.Home.MyBox.route) {
        MyBox(modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeAppBar() {
    TopAppBar(
        title = {},
        modifier = Modifier.height(80.dp),
        actions = {
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.wimm_logo),
                contentDescription = null
            )
            IconButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = { /* todo */ }
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = stringResource(R.string.my_profile)
                )
            }
        }
    )
}

@Composable
fun HomeBottomBar(navController: NavController) {
    val tabs = remember { HomeTab.entries.toTypedArray() }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: Destination.Home.Feed.route

    val routes = remember { HomeTab.entries.map { it.route } }
    if (currentRoute in routes) {
        NavigationBar(
            Modifier.windowInsetsBottomHeight(
                WindowInsets.navigationBars.add(WindowInsets(bottom = 56.dp))
            )
        ) {
            tabs.forEach { tab ->
                val selected = currentRoute == tab.route
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(if (selected) tab.selectedIcon else tab.unselectedIcon),
                            contentDescription = null
                        )
                    },
                    selected = selected,
                    onClick = {
                        if (tab.route != currentRoute) {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    alwaysShowLabel = false,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    }
}

enum class HomeTab(
    @StringRes val title: Int,
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int,
    val route: String
) {
    FEED(
        R.string.menu_feed,
        R.drawable.ic_browse_unselected,
        R.drawable.ic_browse,
        Destination.Home.Feed.route
    ),
    MENTORS(
        R.string.menu_mentors,
        R.drawable.ic_mentor_unselected,
        R.drawable.ic_mentor,
        Destination.Home.Mentors.route
    ),
    MY_BOX(
        R.string.menu_box,
        R.drawable.ic_box_unselected,
        R.drawable.ic_box,
        Destination.Home.MyBox.route
    ),
    SEARCH(
        R.string.menu_search,
        R.drawable.ic_search_unselected,
        R.drawable.ic_search,
        Destination.Home.MyBox.route
    ),
    PROFILE(
        R.string.menu_me,
        R.drawable.ic_me_unselected,
        R.drawable.ic_me_selected,
        Destination.Home.Profile.route
    ),
}