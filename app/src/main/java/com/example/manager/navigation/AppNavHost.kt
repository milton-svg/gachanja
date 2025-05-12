package com.example.manager.navigation

import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.manager.ui.theme.screens.dashboard.DashboardScreen
import com.example.manager.ui.theme.screens.houses.UpdatehouseScreen
import com.example.manager.ui.theme.screens.houses.AddhouseScreen
import com.example.manager.ui.theme.screens.houses.BookedHousesScreen
import com.example.manager.ui.theme.screens.houses.HouseBookingScreen
import com.example.manager.ui.theme.screens.houses.ViewHouse
import com.example.manager.ui.theme.screens.login.LoginScreen
import com.example.manager.ui.theme.screens.register.RegisterScreen
import com.example.manager.ui.theme.screens.register.SplashScreen

@Composable
fun AppNavHost(navController:NavHostController = rememberNavController(),startDestination:String= ROUTE_SPLASH){
    NavHost(navController=navController,startDestination=startDestination){
        composable(ROUTE_SPLASH){ SplashScreen {
            navController.navigate(ROUTE_REGISTER) {
                popUpTo(ROUTE_SPLASH){inclusive=true}} }}
        composable(ROUTE_REGISTER) { RegisterScreen(navController) }
        composable(ROUTE_LOGIN) { LoginScreen(navController) }
        composable(ROUTE_DASHBOARD){ DashboardScreen(navController) }
        composable(ROUTE_ADD_HOUSES){ AddhouseScreen (navController) }
        composable(ROUTE_VIEW_HOUSES){ ViewHouse(navController) }
//        composable(ROUTE_BOOK_HOUSE) { HouseBookingScreen(navController) }
        composable("book_house/{houseId}") { backStackEntry ->
            val houseId = backStackEntry.arguments?.getString("houseId") ?: ""
            HouseBookingScreen(navController = navController, houseId = houseId)
        }

        composable(ROUTE_BOOKED_HOUSE) { BookedHousesScreen(navController)  }
        composable("$ROUTE_UPDATE_HOUSES/{productId}") {
                passedData -> UpdatehouseScreen(
            navController, passedData.arguments?.getString("houseId")!! )
        }
    }

}