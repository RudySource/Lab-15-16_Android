package com.rudy.student_planner.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rudy.student_planner.data.sampleSubjects
import com.rudy.student_planner.ui_model.*
import com.rudy.student_planner.data.calendar_lessons

@Composable
fun StudentPlannerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onSubjectClick = { subjectId ->
                    navController.navigate(Screen.Details.createRoute(subjectId))
                },
                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                },
                onCalendarClick = {
                    navController.navigate(Screen.Calendar.route)
                }
            )
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("subjectId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
            DetailsScreen(
                subjectId = subjectId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Calendar.route) {
            CalendarScreen(
                onNavigateBack = { navController.popBackStack() },
                onLessonClick = { lessonId ->
                    val lesson = calendar_lessons.find { it.id == lessonId }
                    if (lesson != null) {
                        val subject = sampleSubjects.find { it.name == lesson.subjectName }
                        if (subject != null) {
                            navController.navigate(Screen.Details.createRoute(subject.id))
                        }
                    }
                }
            )
        }
    }
}
