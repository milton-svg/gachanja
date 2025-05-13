//package com.example.manager.ui.theme.screens.houses
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import coil.compose.AsyncImage
//import com.example.manager.data.HouseViewModel
//import com.example.manager.R
//import com.example.manager.models.HouseModel
//import com.example.manager.navigation.ROUTE_UPDATE_HOUSES
//
////
//@Composable
//fun ViewHouse(navController: NavHostController) {
//    val context = LocalContext.current
//    val houseRepository = HouseViewModel()
//    val emptyUploadState = remember { mutableStateOf(HouseModel()) }
//    val emptyUploadListState = remember { mutableStateListOf<HouseModel>() }
//    val houses = houseRepository.viewHouses(emptyUploadState, emptyUploadListState, context)
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "All Houses",
//            fontSize = 30.sp,
//            fontFamily = FontFamily.SansSerif,
//            color = Color.Black,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(bottom = 20.dp)
//        )
//
//        LazyColumn(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            items(houses) {
//                HouseItem(
//                    housesize = it.housesize,
//                    houselocation = it.houselocation,
//                    houseprice = it.houseprice,
//                    desc = it.desc,
//                    houseId = it.houseId,
//                    imageUrl = it.imageUrl,
//                    navController = navController,
//                    houseRepository = houseRepository
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun HouseItem(
//    housesize: String,
//    houseprice: String,
//    houselocation: String,
//    houseId: String,
//    desc: String,
//    imageUrl: String,
//    navController: NavHostController,
//    houseRepository: HouseViewModel
//) {
//    val context = LocalContext.current
//
//    // Card with elevation, padding, and rounded corners
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .height(250.dp),
//        shape = MaterialTheme.shapes.medium,
//        elevation = 8.dp,
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        Row(modifier = Modifier.fillMaxSize()) {
//            // Image Section
//            AsyncImage(
//                model = imageUrl,
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .width(120.dp)
//                    .fillMaxHeight()
//                    .clip(RoundedCornerShape(10.dp))
//            )
//
//            // Content Section
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .weight(1f)
//                    .verticalScroll(rememberScrollState())
//            ) {
//                Text(
//                    text = housesize,
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//                Text(
//                    text = houseprice,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Gray,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//                Text(
//                    text = houselocation,
//                    fontSize = 16.sp,
//                    color = Color.Gray,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//                Text(
//                    text = desc,
//                    fontSize = 14.sp,
//                    color = Color.Black,
//                    modifier = Modifier.padding(bottom = 12.dp)
//                )
//
//                // Buttons Row
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(10.dp)
//                ) {
//                    Button(
//                        onClick = {
//                            houseRepository.deleteHouse(context, houseId, navController)
//                        },
//                        shape = RoundedCornerShape(10.dp),
//                        colors = ButtonDefaults.buttonColors(Color.Red),
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text(
//                            text = "REMOVE",
//                            color = Color.White,
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 14.sp
//                        )
//                    }
//
//                    Button(
//                        onClick = {
//                            navController.navigate("book_house/$houseId")
//                        },
//                        shape = RoundedCornerShape(10.dp),
//                        colors = ButtonDefaults.buttonColors(Color.Blue),
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text(
//                            text = "BOOK",
//                            color = Color.White,
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 14.sp
//                        )
//                    }
//
//                    Button(
//                        onClick = {
//                            navController.navigate("$ROUTE_UPDATE_HOUSES/$houseId")
//                        },
//                        shape = RoundedCornerShape(10.dp),
//                        colors = ButtonDefaults.buttonColors(Color.Green),
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text(
//                            text = "UPDATE",
//                            color = Color.White,
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 14.sp
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun ViewHousePreview() {
//    ViewHouse(rememberNavController())
//}
//package com.example.manager.ui.theme.screens.houses
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import coil.compose.AsyncImage
//import com.example.manager.data.HouseViewModel
//import com.example.manager.models.HouseModel
//
//const val ROUTE_UPDATE_HOUSES = "update_house" // Ensure this is defined
//
////@Composable
//fun ViewHouse(navController: NavHostController) {
//    val context = LocalContext.current
//    val houseRepository = HouseViewModel()
//    val emptyUploadState = remember { mutableStateOf(HouseModel()) }
//    val emptyUploadListState = remember { mutableStateListOf<HouseModel>() }
//
//    val houses = houseRepository.viewHouses(emptyUploadState, emptyUploadListState, context)
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "All Houses",
//            fontSize = 30.sp,
//            fontFamily = FontFamily.SansSerif,
//            color = Color.Black,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(bottom = 20.dp)
//        )
//
//        LazyColumn(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            items(houses) { house ->
//                HouseItem(
//                    housesize = house.housesize,
//                    houselocation = house.houselocation,
//                    houseprice = house.houseprice,
//                    desc = house.desc,
//                    houseId = house.houseId,
//                    imageUrl = house.imageUrl,
//                    navController = navController,
//                    houseRepository = houseRepository
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun HouseItem(
//    housesize: String,
//    houseprice: String,
//    houselocation: String,
//    houseId: String,
//    desc: String,
//    imageUrl: String,
//    navController: NavHostController,
//    houseRepository: HouseViewModel
//) {
//    val context = LocalContext.current
//
//    // Card with elevation, padding, and rounded corners
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .height(250.dp),
//        shape = MaterialTheme.shapes.medium,
//
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        Row(modifier = Modifier.fillMaxSize()) {
//            // Image Section
//            AsyncImage(
//                model = imageUrl,
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .width(120.dp)
//                    .fillMaxHeight()
//                    .clip(RoundedCornerShape(10.dp))
//            )
//
//            // Content Section
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .weight(1f)
//                    .verticalScroll(rememberScrollState())
//            ) {
//                Text(
//                    text = housesize,
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//                Text(
//                    text = houseprice,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Gray,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//                Text(
//                    text = houselocation,
//                    fontSize = 16.sp,
//                    color = Color.Gray,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//                Text(
//                    text = desc,
//                    fontSize = 14.sp,
//                    color = Color.Black,
//                    modifier = Modifier.padding(bottom = 12.dp)
//                )
//
//                // Buttons Row
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(10.dp)
//                ) {
//                    Button(
//                        onClick = {
//                            houseRepository.deleteHouse(context, houseId, navController)
//                        },
//                        shape = RoundedCornerShape(10.dp),
//                        colors = ButtonDefaults.buttonColors(Color.Black),
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text(
//                            text = "REMOVE",
//                            color = Color.Red,
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 14.sp
//                        )
//                    }
//
//                    Button(
//                        onClick = {
//                            navController.navigate("book_house/$houseId")
//                        },
//                        shape = RoundedCornerShape(10.dp),
//                        colors = ButtonDefaults.buttonColors(Color.Black),
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text(
//                            text = "BOOK",
//                            color = Color.White,
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 14.sp
//                        )
//                    }
//
//                    Button(
//                        onClick = {
//                            navController.navigate("$ROUTE_UPDATE_HOUSES/$houseId")
//                        },
//                        shape = RoundedCornerShape(10.dp),
//                        colors = ButtonDefaults.buttonColors(Color.Black),
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text(
//                            text = "UPDATE",
//                            color = Color.Green,
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 14.sp
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun ViewHousePreview() {
//    ViewHouse(rememberNavController())
//}
//@Composable
//fun ViewHouse(navController: NavHostController) {
//    val context = LocalContext.current
//    val houseRepository = HouseViewModel()
//    val emptyUploadState = remember { mutableStateOf(HouseModel()) }
//    val emptyUploadListState = remember { mutableStateListOf<HouseModel>() }
//
//    val houses = houseRepository.viewHouses(emptyUploadState, emptyUploadListState, context)
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "All Houses",
//            fontSize = 28.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(vertical = 12.dp)
//        )
//
//        LazyColumn(
//            verticalArrangement = Arrangement.spacedBy(12.dp),
//            modifier = Modifier.fillMaxSize()
//        ) {
//            items(houses) { house ->
//                HouseItem(
//                    housesize = house.housesize,
//                    houselocation = house.houselocation,
//                    houseprice = house.houseprice,
//                    desc = house.desc,
//                    houseId = house.houseId,
//                    imageUrl = house.imageUrl,
//                    navController = navController,
//                    houseRepository = houseRepository
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun HouseItem(
//    housesize: String,
//    houseprice: String,
//    houselocation: String,
//    houseId: String,
//    desc: String,
//    imageUrl: String,
//    navController: NavHostController,
//    houseRepository: HouseViewModel
//) {
//    val context = LocalContext.current
//
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(4.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Row(modifier = Modifier.padding(12.dp)) {
//            AsyncImage(
//                model = imageUrl,
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(width = 120.dp, height = 140.dp)
//                    .clip(RoundedCornerShape(12.dp))
//            )
//
//            Spacer(modifier = Modifier.width(12.dp))
//
//            Column(
//                modifier = Modifier.weight(1f),
//                verticalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column {
//                    Text("Size: $housesize", fontWeight = FontWeight.Bold, fontSize = 16.sp)
//                    Text("Price: $houseprice", color = Color(0xFF4CAF50), fontSize = 14.sp)
//                    Text("Location: $houselocation", fontSize = 14.sp)
//                    Text("Description: $desc", fontSize = 13.sp, maxLines = 2)
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    ActionButton("Remove", Color.Red) {
//                        houseRepository.deleteHouse(context, houseId, navController)
//                    }
//                    ActionButton("Book", MaterialTheme.colorScheme.primary) {
//                        navController.navigate("book_house/$houseId")
//                    }
//                    ActionButton("Update", Color(0xFF43A047)) {
//                        navController.navigate("$ROUTE_UPDATE_HOUSES/$houseId")
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ActionButton(label: String, color: Color, onClick: () -> Unit) {
//    Button(
//        onClick = onClick,
//        shape = RoundedCornerShape(8.dp),
//        colors = ButtonDefaults.buttonColors(containerColor = color),
//        modifier = Modifier.weight(1f)
//    ) {
//        Text(text = label, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ViewHousePreview() {
//    ViewHouse(rememberNavController())
//}
//package com.example.manager.ui.theme.screens.houses
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import coil.compose.AsyncImage
//import com.example.manager.data.HouseViewModel
//import com.example.manager.models.HouseModel
//
//const val ROUTE_UPDATE_HOUSES = "update_house"
//
//@Composable
//fun ViewHouse(navController: NavHostController) {
//    val context = LocalContext.current
//    val houseRepository = HouseViewModel()
//    val emptyHouseState = remember { mutableStateOf(HouseModel()) }
//    val houseListState = remember { mutableStateListOf<HouseModel>() }
//
//    val houses = houseRepository.viewHouses(emptyHouseState, houseListState, context)
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "All Houses",
//            fontSize = 28.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(vertical = 12.dp)
//        )
//
//        LazyColumn(
//            verticalArrangement = Arrangement.spacedBy(12.dp),
//            modifier = Modifier.fillMaxSize()
//        ) {
//            items(houses) { house ->
//                HouseCard(
//                    house = house,
//                    navController = navController,
//                    houseRepository = houseRepository
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun HouseCard(
//    house: HouseModel,
//    navController: NavHostController,
//    houseRepository: HouseViewModel
//) {
//    val context = LocalContext.current
//
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(4.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(IntrinsicSize.Min)
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(13.dp)
//                .fillMaxWidth()
//        ) {
//            AsyncImage(
//                model = house.imageUrl,
//                contentDescription = "House image",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(width = 120.dp, height = 140.dp)
//                    .clip(RoundedCornerShape(12.dp))
//            )
//
//            Spacer(modifier = Modifier.width(12.dp))
//
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxHeight(),
//                verticalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column {
//                    Text("Size: ${house.housesize}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
//                    Text("Price: ${house.houseprice}", color = Color(0xFF388E3C), fontSize = 14.sp)
//                    Text("Location: ${house.houselocation}", fontSize = 14.sp)
//                    Text(
//                        text = "Description: ${house.desc}",
//                        fontSize = 13.sp,
//                        maxLines = 2
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    ActionButton("Remove", Color.Red) {
//                        houseRepository.deleteHouse(context, house.houseId, navController)
//                    }
//                    ActionButton("Book", MaterialTheme.colorScheme.primary) {
//                        navController.navigate("book_house/${house.houseId}")
//                    }
//                    ActionButton("Update", Color(0xFF43A047)) {
//                        navController.navigate("$ROUTE_UPDATE_HOUSES/${house.houseId}")
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ActionButton(label: String, backgroundColor: Color, onClick: () -> Unit) {
//    Button(
//        onClick = onClick,
//        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
//        shape = RoundedCornerShape(10.dp),
//        modifier = Modifier
//            .height(40.dp)
//    ) {
//        Text(
//            text = label,
//            color = Color.White,
//            fontWeight = FontWeight.SemiBold,
//            fontSize = 13.sp
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ViewHousePreview() {
//    ViewHouse(navController = rememberNavController())
//}
package com.example.manager.ui.theme.screens.houses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.manager.data.HouseViewModel
import com.example.manager.models.HouseModel

const val ROUTE_UPDATE_HOUSES = "update_house"

@Composable
fun ViewHouse(navController: NavHostController) {
    val context = LocalContext.current
    val houseRepository = HouseViewModel()
    val emptyHouseState = remember { mutableStateOf(HouseModel()) }
    val houseListState = remember { mutableStateListOf<HouseModel>() }

    val houses = houseRepository.viewHouses(emptyHouseState, houseListState, context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "All Houses",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(houses) { house ->
                HouseCard(
                    house = house,
                    navController = navController,
                    houseRepository = houseRepository
                )
            }
        }
    }
}

@Composable
fun HouseCard(
    house: HouseModel,
    navController: NavHostController,
    houseRepository: HouseViewModel
) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = house.imageUrl,
                contentDescription = "House image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Size: ${house.housesize}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("Price: ${house.houseprice}", color = Color(0xFF388E3C), fontSize = 14.sp)
            Text("Location: ${house.houselocation}", fontSize = 14.sp)
            Text("desc: ${house.desc}", fontSize = 13.sp, maxLines = 2)

            Spacer(modifier = Modifier.height(8.dp))

            // Use a column for buttons for better stacking on narrow screens
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ActionButton("Remove", Color.Red) {
                    houseRepository.deleteHouse(context, house.houseId, navController)
                }
                ActionButton("Book", MaterialTheme.colorScheme.primary) {
                    navController.navigate("book_house/${house.houseId}")
                }
//                ActionButton("Update", Color(0xFF43A047)) {
//                    navController.navigate("$ROUTE_UPDATE_HOUSES/${house.houseId}")
//                }
            }
        }
    }
}

@Composable
fun ActionButton(label: String, backgroundColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
    ) {
        Text(
            text = label,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ViewHousePreview() {
    ViewHouse(navController = rememberNavController())
}
