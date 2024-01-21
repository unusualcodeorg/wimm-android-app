package com.whereismymotivation.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.whereismymotivation.R
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.profile.ProfileTab
import com.whereismymotivation.ui.search.SearchMode
import com.whereismymotivation.ui.theme.AppTheme

@Keep
enum class HomeTab(
    @StringRes val title: Int,
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int,
    val route: String,
    val destRoute: String,
) {
    FEED(
        R.string.menu_feed,
        R.drawable.ic_browse_unselected,
        R.drawable.ic_browse,
        Destination.Home.Feed.route,
        Destination.Home.Feed.route,
    ),
    MENTORS(
        R.string.menu_mentors,
        R.drawable.ic_mentor_unselected,
        R.drawable.ic_mentor,
        Destination.Home.Mentors.route,
        Destination.Home.Mentors.route,
    ),
    MY_BOX(
        R.string.menu_box,
        R.drawable.ic_box_unselected,
        R.drawable.ic_box,
        Destination.Home.MyBox.route,
        Destination.Home.MyBox.route,
    ),
    SEARCH(
        R.string.menu_search,
        R.drawable.ic_search_unselected,
        R.drawable.ic_search,
        Destination.Home.Search.route,
        Destination.Home.Search.dynamicRoute(SearchMode.UNIVERSAL.name),
    ),
    PROFILE(
        R.string.menu_me,
        R.drawable.ic_me_unselected,
        R.drawable.ic_me_selected,
        Destination.Home.Profile.route,
        Destination.Home.Profile.dynamicRoute(ProfileTab.MOOD.name),
    ),
}

@Composable
fun HomeBottomBar(navController: NavController) {

    val tabs = remember { HomeTab.entries.toTypedArray().asList() }
    val routes = remember { HomeTab.entries.map { it.route } }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: Destination.Home.Feed.route

    HomeBottomBarView(
        tabs = tabs,
        routes = routes,
        currentRoute = currentRoute,
        tabClick = {
            if (it.route != currentRoute) {
                navController.navigate(it.destRoute) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    )
}

@Composable
private fun HomeBottomBarView(
    tabs: List<HomeTab>,
    routes: List<String>,
    currentRoute: String,
    tabClick: (HomeTab) -> Unit
) {

    if (currentRoute in routes) {
        NavigationBar(
            Modifier
                .windowInsetsBottomHeight(
                    WindowInsets.navigationBars.add(WindowInsets(bottom = 56.dp))
                ),
            tonalElevation = 4.dp
        ) {
            tabs.forEach { tab ->
                val selected = currentRoute == tab.route
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(if (selected) tab.selectedIcon else tab.unselectedIcon),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                    selected = selected,
                    onClick = { tabClick(tab) },
                    alwaysShowLabel = false,
                    modifier = Modifier.navigationBarsPadding(),
                )
            }
        }
    }
}

@Preview("Light")
@Composable
private fun HomeBottomBarLightPreview() {
    AppTheme {
        HomeBottomBarView(
            tabs = HomeTab.entries.toTypedArray().asList(),
            routes = HomeTab.entries.map { it.route },
            currentRoute = HomeTab.MENTORS.route,
        ) {}
    }
}

@Preview("Dark")
@Composable
private fun HomeBottomBarDarkPreview() {
    AppTheme(dark = true) {
        HomeBottomBarView(
            tabs = HomeTab.entries.toTypedArray().asList(),
            routes = HomeTab.entries.map { it.route },
            currentRoute = HomeTab.MENTORS.route,
        ) {}
    }
}