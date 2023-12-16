package com.whereismymotivation.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
        Destination.Home.Search.route
    ),
    PROFILE(
        R.string.menu_me,
        R.drawable.ic_me_unselected,
        R.drawable.ic_me_selected,
        Destination.Home.Profile.route
    ),
}