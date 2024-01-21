package com.whereismymotivation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.whereismymotivation.ui.content.ContentRedirection
import com.whereismymotivation.ui.content.ContentViewModel
import com.whereismymotivation.ui.content.YouTubeContent
import com.whereismymotivation.ui.content.YoutubeViewModel
import com.whereismymotivation.ui.feed.Feed
import com.whereismymotivation.ui.feed.FeedViewModel
import com.whereismymotivation.ui.info.InfoViewModel
import com.whereismymotivation.ui.info.ServerUnreachableInfo
import com.whereismymotivation.ui.journal.JournalsViewModel
import com.whereismymotivation.ui.login.Login
import com.whereismymotivation.ui.login.LoginViewModel
import com.whereismymotivation.ui.mentor.Mentor
import com.whereismymotivation.ui.mentor.MentorViewModel
import com.whereismymotivation.ui.mentors.ExploreMentors
import com.whereismymotivation.ui.mentors.ExploreMentorsViewModel
import com.whereismymotivation.ui.mentors.Mentors
import com.whereismymotivation.ui.mentors.MentorsViewModel
import com.whereismymotivation.ui.moods.MoodsViewModel
import com.whereismymotivation.ui.mybox.MyBox
import com.whereismymotivation.ui.mybox.MyBoxViewModel
import com.whereismymotivation.ui.onboarding.Onboarding
import com.whereismymotivation.ui.onboarding.OnboardingViewModel
import com.whereismymotivation.ui.profile.Profile
import com.whereismymotivation.ui.profile.ProfileViewModel
import com.whereismymotivation.ui.search.Search
import com.whereismymotivation.ui.search.SearchViewModel
import com.whereismymotivation.ui.search.SuggestionViewModel
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
    ) {
        // Splash
        composable(Destination.Splash.route) {
            val viewModel: SplashViewModel = hiltViewModel(key = SplashViewModel.TAG)
            Splash(modifier, viewModel)
        }

        // ServerUnreachable
        composable(Destination.ServerUnreachable.route) {
            val viewModel: InfoViewModel = hiltViewModel(key = InfoViewModel.TAG)
            ServerUnreachableInfo(modifier, viewModel)
        }

        // Onboarding
        composable(Destination.Onboarding.route) {
            val viewModel: OnboardingViewModel = hiltViewModel(key = OnboardingViewModel.TAG)
            Onboarding(modifier, viewModel)
        }

        // Login
        composable(Destination.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel(key = LoginViewModel.TAG)
            Login(modifier, viewModel)
        }

        // Home
        navigation(
            route = Destination.Home.route,
            startDestination = Destination.Home.Feed.route
        ) {

            // Home.Feed
            composable(Destination.Home.Feed.route) {
                val viewModel: FeedViewModel = hiltViewModel(key = FeedViewModel.TAG)
                Feed(modifier, viewModel)
            }

            // Home.Mentors
            composable(Destination.Home.Mentors.route) {
                val viewModel: MentorsViewModel = hiltViewModel(key = MentorsViewModel.TAG)
                Mentors(modifier, viewModel)
            }

            // Home.Search
            composable(
                route = Destination.Home.Search.route,
                arguments = Destination.Home.Search.navArguments
            ) {
                val viewModel: SearchViewModel = hiltViewModel(key = SearchViewModel.TAG)
                val sViewModel: SuggestionViewModel = hiltViewModel(key = SuggestionViewModel.TAG)
                Search(modifier, viewModel, sViewModel)
            }

            // Home.Profile
            composable(
                route = Destination.Home.Profile.route,
                arguments = Destination.Home.Profile.navArguments,
                deepLinks = listOf(navDeepLink {
                    uriPattern = Destination.Home.Profile.deeplink
                }),
            ) {
                val profileViewModel: ProfileViewModel = hiltViewModel(key = ProfileViewModel.TAG)
                val moodsViewModel: MoodsViewModel = hiltViewModel(key = MoodsViewModel.TAG)
                val journalsViewModel: JournalsViewModel =
                    hiltViewModel(key = JournalsViewModel.TAG)
                Profile(modifier, profileViewModel, moodsViewModel, journalsViewModel)
            }

            // Home.MyBox
            composable(
                Destination.Home.MyBox.route,
                deepLinks = listOf(navDeepLink {
                    uriPattern = Destination.Home.MyBox.deeplink
                }),
            ) {
                val viewModel: MyBoxViewModel = hiltViewModel(key = MyBoxViewModel.TAG)
                MyBox(modifier, viewModel)
            }
        }

        // Mentor
        composable(
            route = Destination.Mentor.route,
            arguments = Destination.Mentor.navArguments
        ) {
            val viewModel: MentorViewModel = hiltViewModel(key = MentorViewModel.TAG)
            Mentor(modifier, viewModel)
        }

        // ExploreMentors
        composable(Destination.ExploreMentors.route) {
            val viewModel: ExploreMentorsViewModel =
                hiltViewModel(key = ExploreMentorsViewModel.TAG)
            ExploreMentors(modifier, viewModel)
        }

        // Topic
        composable(
            route = Destination.Topic.route,
            arguments = Destination.Topic.navArguments
        ) {
            val viewModel: TopicViewModel = hiltViewModel(key = TopicViewModel.TAG)
            Topic(modifier, viewModel)
        }

        // YouTube
        composable(
            route = Destination.YouTube.route,
            arguments = Destination.YouTube.navArguments
        ) {
            val viewModel: YoutubeViewModel = hiltViewModel(key = YoutubeViewModel.TAG)
            YouTubeContent(modifier, viewModel)
        }

        // Content Redirection
        composable(
            route = Destination.Content.route,
            arguments = Destination.Content.navArguments,
            deepLinks = listOf(navDeepLink {
                uriPattern = Destination.Content.deeplink
            })
        ) {
            val viewModel: ContentViewModel = hiltViewModel(key = ContentViewModel.TAG)
            ContentRedirection(modifier, viewModel)
        }
    }
}
