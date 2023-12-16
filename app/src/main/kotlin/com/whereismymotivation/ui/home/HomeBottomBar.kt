package com.whereismymotivation.ui.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.Icon
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
import com.whereismymotivation.ui.navigation.Destination

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
        currentRoute = currentRoute
    ) {
        if (it.route != currentRoute) {
            navController.navigate(it.route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }
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
                    onClick = { tabClick(tab) },
                    alwaysShowLabel = false,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeBottomBarPreview() {
    HomeBottomBarView(
        tabs = HomeTab.entries.toTypedArray().asList(),
        routes = HomeTab.entries.map { it.route },
        currentRoute = HomeTab.MENTORS.route,
    ) {}
}