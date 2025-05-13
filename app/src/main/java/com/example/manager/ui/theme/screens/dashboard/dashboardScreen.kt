
package com.example.manager.ui.theme.screens.dashboard

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.manager.R
import com.example.manager.navigation.ROUTE_ADD_HOUSES
import com.example.manager.navigation.ROUTE_BOOKED_HOUSE
import com.example.manager.navigation.ROUTE_BOOK_HOUSE
import com.example.manager.navigation.ROUTE_SPLASH
import com.example.manager.navigation.ROUTE_VIEW_HOUSES

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    val selectedItem = remember { mutableStateOf(0) }
    val context = LocalContext.current


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rental") },
                navigationIcon = {
//                    IconButton(onClick = {}) { Icon(Icons.Filled.Search, contentDescription = "Search") }
                    IconButton(onClick = { navController.navigate(ROUTE_SPLASH)}) { Icon(Icons.Filled.Close, contentDescription = "CLOSE APP") }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
                )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF1976D2)) {
                NavigationBarItem(
                    selected = selectedItem.value == 0,
                    onClick = {
                        selectedItem.value = 0
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:0743237128")
                        }
                        context.startActivity(intent)
                    },
                    icon = { Icon(Icons.Filled.Phone, contentDescription = "Phone") },
                    label = { Text("Phone") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedItem.value == 1,
                    onClick = {
                        selectedItem.value = 1
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:info@emobilis.ac.ke")
                            putExtra(Intent.EXTRA_SUBJECT, "Inquiry")
                            putExtra(Intent.EXTRA_TEXT, "Hello, how can I get a house to rent?")
                        }
                        context.startActivity(intent)
                    },
                    icon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
                    label = { Text("Email") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedItem.value == 2,
                    onClick = {
                        selectedItem.value = 2
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Download the app here: https://www.download.com")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    },
                    icon = { Icon(Icons.Filled.Share, contentDescription = "Share") },
                    label = { Text("Share") },
                    alwaysShowLabel = true
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Image(
                painter = painterResource(id = R.drawable.img_6),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    DashboardCard("Add Rentals") { navController.navigate(ROUTE_ADD_HOUSES) }
                    DashboardCard("View Houses") { navController.navigate(ROUTE_VIEW_HOUSES) }
//                    DashboardCard("book a house") { navController.navigate(ROUTE_VIEW_HOUSES)}
                    DashboardCard("booked houses") {navController.navigate(ROUTE_BOOKED_HOUSE) }


                }
            }
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    DashboardCard("Book a house") { navController.navigate(ROUTE_VIEW_HOUSES)}
                    DashboardCard("Booked houses") {navController.navigate(ROUTE_BOOKED_HOUSE) }


                }
            }
        }
        }
    }


@Composable
fun DashboardCard(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .width(150.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(rememberNavController())
}
