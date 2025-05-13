
package com.example.manager.ui.theme.screens.houses



import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.database.*

data class BookedHouse(
    val key: String = "",
    val userName: String = "",
    val usercontact:String="",
    val houseId: String = "",
    val timestamp: Long = 0L,
    val houseDetails: Map<String, Any>? = null
)

@Composable
fun BookedHousesScreen(navController: NavController) {
    val context = LocalContext.current
    val database = FirebaseDatabase.getInstance().getReference("Bookings")
    val bookedHouses = remember { mutableStateListOf<BookedHouse>() }

    LaunchedEffect(Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookedHouses.clear()
                for (child in snapshot.children) {
                    val key = child.key ?: continue
                    val userName = child.child("userName").getValue(String::class.java) ?: ""
                    val usercontact = child.child("usercontact").getValue(String::class.java) ?: ""
                    val houseId = child.child("houseId").getValue(String::class.java) ?: ""
                    val timestamp = child.child("timestamp").getValue(Long::class.java) ?: 0L
                    val houseDetails = child.child("houseDetails").value as? Map<String, Any>
                    bookedHouses.add(BookedHouse(key, userName, houseId,usercontact ,timestamp, houseDetails))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load bookings", Toast.LENGTH_SHORT).show()
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Booked Houses", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(bookedHouses) { booked ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("User: ${booked.userName}", style = MaterialTheme.typography.titleMedium)
                        Text("contact: ${booked.houseId}")
                        Text("house Id: ${booked.usercontact}", style = MaterialTheme.typography.titleMedium)

                        booked.houseDetails?.let { details ->
                            Text("Details:", style = MaterialTheme.typography.titleSmall)
                            details.forEach { (key, value) ->
                                if (key != "imageUrl") {
                                    Text("$key: $value")
                                }
                            }

                            val imageUrl = details["imageUrl"] as? String
                            if (!imageUrl.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = "House Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                database.child(booked.key).removeValue().addOnSuccessListener {
                                    Toast.makeText(context, "Booking deleted", Toast.LENGTH_SHORT).show()
                                }.addOnFailureListener {
                                    Toast.makeText(context, "Failed to delete booking", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}
