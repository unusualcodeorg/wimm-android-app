package com.whereismymotivation.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereismymotivation.R
import com.whereismymotivation.ui.WimmApp
import com.whereismymotivation.ui.home.Home
import com.whereismymotivation.ui.mentors.Mentors
import com.whereismymotivation.ui.mybox.MyBox
import com.whereismymotivation.ui.profile.Profile
import com.whereismymotivation.ui.search.Search

fun NavGraphBuilder.main(
    modifier: Modifier = Modifier
) {
    composable(MainTab.HOME.route) {
        Home(
            modifier
        )
    }
    composable(MainTab.MENTORS.route) {
        Mentors(
            modifier
        )
    }
    composable(MainTab.SEARCH.route) {
        Search(
            modifier
        )
    }
    composable(MainTab.PROFILE.route) {
        Profile(
            modifier
        )
    }

    composable(MainTab.MY_BOX.route) {
        MyBox(
            modifier
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar() {
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

enum class MainTab(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    HOME(R.string.menu_home, R.drawable.ic_browse_unselected, MainTabDestinations.HOME_ROUTE),
    MENTORS(
        R.string.menu_mentors,
        R.drawable.ic_mentor_unselected,
        MainTabDestinations.MENTORS_ROUTE
    ),
    MY_BOX(R.string.menu_box, R.drawable.ic_box_unselected, MainTabDestinations.MY_BOX_ROUTE),
    SEARCH(R.string.menu_search, R.drawable.ic_search_unselected, MainTabDestinations.SEARCH_ROUTE),
    PROFILE(R.string.menu_me, R.drawable.ic_me_unselected, MainTabDestinations.PROFILE_ROUTE),
}

/**
 * Destinations used in the ([WimmApp]).
 */
private object MainTabDestinations {
    const val HOME_ROUTE = "main/home"
    const val MENTORS_ROUTE = "main/mentors"
    const val MY_BOX_ROUTE = "main/my_box"
    const val SEARCH_ROUTE = "main/search"
    const val PROFILE_ROUTE = "main/profilr"
}