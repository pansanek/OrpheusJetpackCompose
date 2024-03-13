package ru.potemkin.orpheusjetpackcompose.presentation.changeprofile

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.Grey
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ChangeLocationProfileScreen(location: LocationItem, onBackPressed: () -> Unit) {
    var locationName by remember { mutableStateOf(location.name) }
    var locationAddress by remember { mutableStateOf(location.address) }
    var locationAbout by remember { mutableStateOf(location.about) }
    var selectedProfilePictureUri by remember {
        mutableStateOf<Uri?>(Uri.parse(location.profilePicture.url))
    }

    val storagePermissionState = rememberPermissionState(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val singleProfilePicturePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedProfilePictureUri = uri }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад",
                            tint = White
                        )
                    }
                },
                backgroundColor = Black,
                actions = {
                    IconButton(onClick = { /* открыть меню */ }) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Сохранить",
                            tint = White
                        )
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .background(Black)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Column {
                        Text(
                            text = "Изображения профиля",
                            style = MaterialTheme.typography.labelLarge,
                            color = White
                        )
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    storagePermissionState.launchPermissionRequest()
                                    if (storagePermissionState.hasPermission) {
                                        try {
                                            singleProfilePicturePickerLauncher.launch(
                                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                            )
                                        } catch (e: SecurityException) {

                                        }
                                    }
                                }
                                .height(200.dp),
                            shape = RoundedCornerShape(
                                bottomEndPercent = 10,
                                bottomStartPercent = 10,
                                topEndPercent = 10,
                                topStartPercent = 10
                            ),
                            color = Black
                        ) {
                            AsyncImage(
                                model = selectedProfilePictureUri,
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = locationName,
                        onValueChange = { locationName = it },
                        label = { Text("Название") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Black,
                            unfocusedIndicatorColor = White,
                            focusedIndicatorColor = White,
                            focusedTextColor = White,
                            cursorColor = White,
                            unfocusedLabelColor = Grey,
                            unfocusedTextColor = Grey
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = locationAddress,
                        onValueChange = { locationAddress = it },
                        label = { Text("Адрес") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Black,
                            unfocusedIndicatorColor = White,
                            focusedIndicatorColor = White,
                            focusedTextColor = White,
                            cursorColor = White,
                            unfocusedLabelColor = Grey,
                            unfocusedTextColor = Grey
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = locationAbout,
                        onValueChange = { locationAbout = it },
                        label = { Text("Описание") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Black,
                            unfocusedIndicatorColor = White,
                            focusedIndicatorColor = White,
                            focusedTextColor = White,
                            cursorColor = White,
                            unfocusedLabelColor = Grey,
                            unfocusedTextColor = Grey
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        },
        contentColor = Black
    )
}