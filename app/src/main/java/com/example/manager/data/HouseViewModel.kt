package com.example.manager.data

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.manager.models.HouseModel
import com.example.manager.models.ImgurResponse
import com.example.manager.navigation.ROUTE_VIEW_HOUSES
import com.example.manager.network.ImgurService
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class HouseViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance().reference.child("Houses")

    private fun getImgurService(): ImgurService {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgur.com/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ImgurService::class.java)
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile("temp_image", ".jpg", context.cacheDir)
            file.outputStream().use { output ->
                inputStream?.copyTo(output)
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun uploadHouseWithImage(
        uri: Uri,
        context: Context,
        housesize: String,
        houselocation: String,
        houseprice: String,
        houseId: String,
        desc: String,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = getFileFromUri(context, uri)
                if (file == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Failed to process image", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", file.name, reqFile)

                val response = getImgurService().uploadImage(body,
                    "Client-ID e341a88bb464a20")

                if (response.isSuccessful) {
                    val imgurResponse: ImgurResponse? = response.body()
                    val imageUrl = imgurResponse?.data?.link ?: ""

                    if (imageUrl.isNotEmpty()) {
                        val houseId = database.push().key ?: ""
                        val house = HouseModel(housesize, houseprice, houselocation, desc, imageUrl, houseId)

                        database.child(houseId).setValue(house)
                            .addOnSuccessListener {
                                viewModelScope.launch(Dispatchers.Main) {
                                    Toast.makeText(context, "House saved successfully", Toast.LENGTH_SHORT).show()
                                    navController.navigate(ROUTE_VIEW_HOUSES)
                                }
                            }
                            .addOnFailureListener {
                                viewModelScope.launch(Dispatchers.Main) {
                                    Toast.makeText(context, "Failed to save house", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Failed to get image URL", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Imgur upload failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Exception: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun updatehouses(
        context: Context,
        navController: NavController,
        houseId: String,
        houseprice: String,
        housesize: String,
        houselocation: String,
        desc: String
    ) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Houses/$houseId")

        val updatedHouse = HouseModel(housesize, houseprice, houselocation, desc, "", houseId)

        databaseReference.setValue(updatedHouse)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "House Updated Successfully", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_VIEW_HOUSES)
                } else {
                    Toast.makeText(context, "House update failed", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun viewHouses(
        selectedHouse: MutableState<HouseModel>,
        houseList: SnapshotStateList<HouseModel>,
        context: Context
    ): SnapshotStateList<HouseModel> {
        val ref = FirebaseDatabase.getInstance().getReference("Houses")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                houseList.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(HouseModel::class.java)
                    value?.let {
                        houseList.add(it)
                    }
                }
                if (houseList.isNotEmpty()) {
                    selectedHouse.value = houseList.first()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch houses: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        return houseList
    }

    fun deleteHouse(
        context: Context,
        houseID: String,
        navController: NavController
    ) {
        AlertDialog.Builder(context)
            .setTitle("Delete House")
            .setMessage("Are you sure you want to delete this house?")
            .setPositiveButton("Yes") { _, _ ->
                val databaseReference = FirebaseDatabase.getInstance().getReference("Houses/$houseID")
                databaseReference.removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "House deleted successfully", Toast.LENGTH_LONG).show()
                        navController.navigate(ROUTE_VIEW_HOUSES)
                    } else {
                        Toast.makeText(context, "House deletion failed", Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}



