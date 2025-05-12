//package com.example.manager.ui.theme.screens.houses
//
//import android.widget.Toast
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.google.firebase.database.FirebaseDatabase
//@Composable
//fun HouseBookingScreen(navController: NavController, houseId: String) {
//    val context = LocalContext.current
//    var userName by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Book a House", style = MaterialTheme.typography.headlineMedium)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = userName,
//            onValueChange = { userName = it },
//            label = { Text("Your Name") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Text("House ID: $houseId", style = MaterialTheme.typography.bodyLarge)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = {
//                if (userName.isNotBlank()) {
//                    val bookingRef = FirebaseDatabase.getInstance()
//                        .getReference("Bookings")
//                        .push()
//
//                    val booking = mapOf(
//                        "userName" to userName,
//                        "houseId" to houseId,
//                        "timestamp" to System.currentTimeMillis()
//                    )
//
//                    bookingRef.setValue(booking)
//                        .addOnSuccessListener {
//                            Toast.makeText(context, "Booking successful!", Toast.LENGTH_SHORT).show()
//                            navController.popBackStack()
//                        }
//                        .addOnFailureListener {
//                            Toast.makeText(context, "Booking failed.", Toast.LENGTH_SHORT).show()
//                        }
//                } else {
//                    Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
//                }
//            }
//        ) {
//            Text("Book Now")
//        }
//    }
//}
//
//
package com.example.manager.ui.theme.screens.houses

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.FirebaseDatabase

@Composable
fun HouseBookingScreen(navController: NavController, houseId: String) {
    val context = LocalContext.current
    var userName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Book a House", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Your Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("House ID: $houseId", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (userName.isNotBlank()) {
                    val db = FirebaseDatabase.getInstance()
                    val houseRef = db.getReference("Houses").child(houseId)
                    val bookingRef = db.getReference("Bookings").push()

                    houseRef.get().addOnSuccessListener { snapshot ->
                        if (snapshot.exists()) {
                            val houseData = snapshot.value
                            val booking = mapOf(
                                "userName" to userName,
                                "houseId" to houseId,
                                "timestamp" to System.currentTimeMillis(),
                                "houseDetails" to houseData
                            )

                            bookingRef.setValue(booking).addOnSuccessListener {
                                houseRef.removeValue() // ✅ remove from available list
                                Toast.makeText(context, "Booking successful!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }.addOnFailureListener {
                                Toast.makeText(context, "Booking failed to save.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "House not found.", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(context, "Error fetching house data.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Book Now")
        }
    }
}
