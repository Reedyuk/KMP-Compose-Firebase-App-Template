package com.jetbrains.kmpapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jetbrains.kmpapp.screens.auth.LoginScreen
import com.jetbrains.kmpapp.screens.auth.SignUpScreen
import com.jetbrains.kmpapp.screens.detail.DetailScreen
import com.jetbrains.kmpapp.screens.list.ListScreen
import kotlinx.serialization.Serializable

@Serializable
object ListDestination

@Serializable
data class DetailDestination(val objectId: Int)

@Serializable
object Login
@Serializable
object SignUp

@Composable
fun App() {
    MaterialTheme(
        colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
    ) {
        Surface {
            val navController: NavHostController = rememberNavController()
            NavHost(navController = navController, startDestination = ListDestination) {
                composable<ListDestination> {
                    ListScreen(
                        navigateToDetails = { objectId ->
                            navController.navigate(DetailDestination(objectId))
                        },
                        navigateToLogin = {
                            navController.navigate(Login)
                        }
                    )
                }
                composable<DetailDestination> { backStackEntry ->
                    DetailScreen(
                        objectId = backStackEntry.toRoute<DetailDestination>().objectId,
                        navigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
                composable<Login> {
                    LoginScreen(
                        navigateBack = {
                            navController.popBackStack()
                        },
                        navigateToSignUp = {
                            navController.navigate(SignUp)
                        }
                    )
                }
                composable<SignUp> {
                    SignUpScreen(
                        navigateBack = {
                            navController.popBackStack()
                        },
                        navigateUp = {
                            navController.popBackStack()
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
