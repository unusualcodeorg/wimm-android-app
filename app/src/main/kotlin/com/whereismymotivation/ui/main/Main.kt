package com.whereismymotivation.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.whereismymotivation.R
import com.whereismymotivation.ui.WimmApp

enum class MainTab(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    HOME(R.string.menu_home, R.drawable.menu_selector_home, MainTabDestinations.HOME_ROUTE),
    MENTORS(R.string.menu_mentors, R.drawable.menu_selector_mentor, MainTabDestinations.MENTORS_ROUTE),
    MY_BOX(R.string.menu_box, R.drawable.menu_selector_box, MainTabDestinations.MY_BOX_ROUTE),
    SEARCH(R.string.menu_search, R.drawable.menu_selector_search, MainTabDestinations.SEARCH_ROUTE),
    ME(R.string.menu_me, R.drawable.menu_selector_me, MainTabDestinations.ME_ROUTE),
}

/**
 * Destinations used in the ([WimmApp]).
 */
private object MainTabDestinations {
    const val HOME_ROUTE = "main/home"
    const val MENTORS_ROUTE = "main/mentors"
    const val MY_BOX_ROUTE = "main/my_box"
    const val SEARCH_ROUTE = "main/search"
    const val ME_ROUTE = "main/me"
}