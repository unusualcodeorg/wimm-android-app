package com.whereismymotivation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation

/**
 * Destinations used in the ([WimmApp]).
 */
object MainDestinations {
    const val LOGIN_ROUTE = "login"
    const val MAIN_ROUTE = "main"
    const val MENTOR_DETAIL_ROUTE = "mentor"
    const val TOPIC_DETAIL_ROUTE = "topic"
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.MAIN_ROUTE,
    showLogin: Boolean = true
) {
    val loggedIn = remember(showLogin) {
        mutableStateOf(!showLogin)
    }

    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.LOGIN_ROUTE) {
            // Intercept back in Login: make it finish the activity
            BackHandler {
                finishActivity()
            }

//            Login(
//                onboardingComplete = {
//                    // Set the flag so that onboarding is not shown next time.
//                    loggedIn.value = true
//                    actions.onboardingComplete()
//                }
//            )
        }
//        navigation(
//            route = MainDestinations.COURSES_ROUTE,
//            startDestination = CourseTabs.FEATURED.route
//        ) {
//            courses(
//                onCourseSelected = actions.openCourse,
//                onboardingComplete = onboardingComplete,
//                navController = navController,
//                modifier = modifier
//            )
//        }
//        composable(
//            "${MainDestinations.COURSE_DETAIL_ROUTE}/{$COURSE_DETAIL_ID_KEY}",
//            arguments = listOf(
//                navArgument(COURSE_DETAIL_ID_KEY) { type = NavType.LongType }
//            )
//        ) { backStackEntry: NavBackStackEntry ->
//            val arguments = requireNotNull(backStackEntry.arguments)
//            val currentCourseId = arguments.getLong(COURSE_DETAIL_ID_KEY)
//            CourseDetails(
//                courseId = currentCourseId,
//                selectCourse = { newCourseId ->
//                    actions.relatedCourse(newCourseId, backStackEntry)
//                },
//                upPress = { actions.upPress(backStackEntry) }
//            )
//        }
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val onboardingComplete: () -> Unit = {
        navController.popBackStack()
    }

//    // Used from COURSES_ROUTE
//    val openCourse = { newCourseId: Long, from: NavBackStackEntry ->
//        // In order to discard duplicated navigation events, we check the Lifecycle
//        if (from.lifecycleIsResumed()) {
//            navController.navigate("${MainDestinations.COURSE_DETAIL_ROUTE}/$newCourseId")
//        }
//    }
//
//    // Used from COURSE_DETAIL_ROUTE
//    val relatedCourse = { newCourseId: Long, from: NavBackStackEntry ->
//        // In order to discard duplicated navigation events, we check the Lifecycle
//        if (from.lifecycleIsResumed()) {
//            navController.navigate("${MainDestinations.COURSE_DETAIL_ROUTE}/$newCourseId")
//        }
//    }
//
//    // Used from COURSE_DETAIL_ROUTE
//    val upPress: (from: NavBackStackEntry) -> Unit = { from ->
//        // In order to discard duplicated navigation events, we check the Lifecycle
//        if (from.lifecycleIsResumed()) {
//            navController.navigateUp()
//        }
//    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
