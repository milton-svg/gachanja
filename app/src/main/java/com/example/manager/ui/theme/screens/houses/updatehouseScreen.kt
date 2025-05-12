package com.example.manager.ui.theme.screens.houses
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.manager.R
import com.example.manager.data.HouseViewModel
import com.google.firebase.database.*
import com.example.manager.models.HouseModel

@Composable
fun UpdatehouseScreen(navController: NavController, houseId: String) {
    val context = LocalContext.current
    val imageUri = rememberSaveable(){ mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { imageUri.value = it }
    }

    var housesize by remember { mutableStateOf("") }
    var houselocation by remember { mutableStateOf("") }
    var houseprice by remember { mutableStateOf("") }
    var houseId by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    val houseViewModel: HouseViewModel = viewModel()

    // Fetch existing data
    val currentDataRef = FirebaseDatabase.getInstance().getReference("Houses/$houseId")
    DisposableEffect(Unit) {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val house = snapshot.getValue(HouseModel::class.java)
                house?.let {
                    housesize = it.housesize
                    houselocation = it.houselocation
                    houseprice = it.houseprice
                    houseId=it.houseId
                    desc = it.desc
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
            }
        }


        currentDataRef.addValueEventListener(listener)
        onDispose { currentDataRef.removeEventListener(listener) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .padding(10.dp)
                .size(200.dp)
        ) {
            if (imageUri.value != null) {
                AsyncImage(
                    model = imageUri.value,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clickable { launcher.launch("image/*") }
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clickable { launcher.launch("image/*") }
                )
            }
        }

        Text(text = "Attach house image")

        OutlinedTextField(
            value = housesize,
            onValueChange = { housesize = it },
            label = { Text("House size") },
            placeholder = { Text("Please enter size") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = houselocation,
            onValueChange = { houselocation = it },
            label = { Text("Location of the house") },
            placeholder = { Text("Please enter location") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = houseprice,
            onValueChange = { houseprice = it },
            label = { Text("Unit Product Price") },
            placeholder = { Text("Please enter unit product price") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Brief description") },
            placeholder = { Text("Please enter house description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            singleLine = false
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { /* Navigate back to house list */ }) {
                Text(text = "All houses")
            }
            Button(onClick = {
                houseViewModel.updatehouses(
                    context = context,
                    navController = navController,
                    housesize = housesize,
                    houselocation = houselocation,
                    houseprice = houseprice,
                    desc = desc,
                    houseId = houseId
                )
            }) {
                Text(text = "UPDATE")
            }
        }
    }
}
