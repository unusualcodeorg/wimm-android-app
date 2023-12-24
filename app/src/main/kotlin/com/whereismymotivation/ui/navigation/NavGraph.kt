package com.whereismymotivation.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.whereismymotivation.ui.content.ContentViewModel
import com.whereismymotivation.ui.content.YouTubeContent
import com.whereismymotivation.ui.feed.Feed
import com.whereismymotivation.ui.feed.FeedViewModel
import com.whereismymotivation.ui.login.Login
import com.whereismymotivation.ui.login.LoginViewModel
import com.whereismymotivation.ui.mentor.Mentor
import com.whereismymotivation.ui.mentor.MentorViewModel
import com.whereismymotivation.ui.mentors.Mentors
import com.whereismymotivation.ui.mentors.MentorsViewModel
import com.whereismymotivation.ui.mybox.MyBox
import com.whereismymotivation.ui.profile.Profile
import com.whereismymotivation.ui.profile.ProfileViewModel
import com.whereismymotivation.ui.search.Search
import com.whereismymotivation.ui.search.SearchViewModel
import com.whereismymotivation.ui.splash.Splash
import com.whereismymotivation.ui.splash.SplashViewModel
import com.whereismymotivation.ui.topic.Topic
import com.whereismymotivation.ui.topic.TopicViewModel

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Destination.Splash.route,
    navigator: Navigator,
    finish: () -> Unit = {},
) {
    NavHandler(
        navController = navController,
        navigator = navigator,
        finish = finish
    )

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        composable(Destination.Splash.route) {
            val viewModel: SplashViewModel = hiltViewModel(key = SplashViewModel.TAG)
            Splash(
                modifier = modifier,
                viewModel = viewModel,
            )
        }
        composable(Destination.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel(key = LoginViewModel.TAG)
            Login(
                modifier = modifier,
                viewModel = viewModel,
            )
        }
        navigation(
            route = Destination.Home.route,
            startDestination = Destination.Home.Feed.route
        ) {
            composable(Destination.Home.Feed.route) {
                val viewModel: FeedViewModel = hiltViewModel(key = FeedViewModel.TAG)
                Feed(modifier, viewModel)
            }
            composable(Destination.Home.Mentors.route) {
                val viewModel: MentorsViewModel = hiltViewModel(key = MentorsViewModel.TAG)
                Mentors(modifier, viewModel)
            }
            composable(Destination.Home.Search.route) {
                val viewModel: SearchViewModel = hiltViewModel(key = SearchViewModel.TAG)
                Search(modifier, viewModel)
            }
            composable(Destination.Home.Profile.route) {
                val viewModel: ProfileViewModel = hiltViewModel(key = ProfileViewModel.TAG)
                Profile(modifier, viewModel)
            }
            composable(Destination.Home.MyBox.route) {
                MyBox(modifier)
            }
        }
        composable(
            route = Destination.Mentor.route,
            arguments = Destination.Mentor.navArguments
        ) {
            val viewModel: MentorViewModel = hiltViewModel(key = MentorViewModel.TAG)
            Mentor(
                modifier = modifier,
                viewModel = viewModel
            )
        }
        composable(
            route = Destination.Topic.route,
            arguments = Destination.Topic.navArguments
        ) {
            val viewModel: TopicViewModel = hiltViewModel(key = TopicViewModel.TAG)
            Topic(
                modifier = modifier,
                viewModel = viewModel
            )
        }
        composable(
            route = Destination.YouTube.route,
            arguments = Destination.YouTube.navArguments
        ) {
            val viewModel: ContentViewModel = hiltViewModel(key = ContentViewModel.TAG)
            YouTubeContent(
                modifier = modifier,
                viewModel = viewModel
            )
        }
        composable(
            route = Destination.Search.route,
            arguments = Destination.Search.navArguments
        ) {
            val viewModel: SearchViewModel = hiltViewModel(key = SearchViewModel.TAG)
            Search(
                modifier = modifier,
                viewModel = viewModel
            )
        }
    }
}
