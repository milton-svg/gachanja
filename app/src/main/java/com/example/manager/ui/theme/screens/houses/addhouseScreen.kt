package com.example.manager.ui.theme.screens.houses

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.manager.data.HouseViewModel
import com.example.manager.R
import com.example.manager.navigation.ROUTE_VIEW_HOUSES
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
@Composable
fun AddhouseScreen(navController: NavController) {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { imageUri.value = it }
    }

    var housesize by remember { mutableStateOf("") }
    var houselocation by remember { mutableStateOf("") }
    var houseprice by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var houseId by remember { mutableStateOf("") }

    val houseViewModel: HouseViewModel = viewModel()

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
                    contentDescription = "Selected image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clickable { launcher.launch("image/*") }
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "Default icon",
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
            placeholder = { Text("Please enter house location") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = houseprice,
            onValueChange = { houseprice = it },
            label = { Text("House Price per month") },
            placeholder = { Text("Please enter the monthly rent") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = houseId,
            onValueChange = { houseId = it },
            label = { Text("House id") },
            placeholder = { Text("Please enter house Id") },
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
            Button(onClick = { navController.navigate(ROUTE_VIEW_HOUSES) }) {
                Text(text = "All houses")
            }

            Button(onClick = {
                imageUri.value?.let {
                    houseViewModel.uploadHouseWithImage(
                        it,
                        context,
                        housesize,
                        houselocation,
                        houseprice,
                        desc,
                        houseId,
                        navController
                    )
                } ?: Toast.makeText(context, "Please pick an image", Toast.LENGTH_SHORT).show()
            }) {
                Text(text = "SAVE")
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddhouseScreenPreview(){
    AddhouseScreen(rememberNavController())
}