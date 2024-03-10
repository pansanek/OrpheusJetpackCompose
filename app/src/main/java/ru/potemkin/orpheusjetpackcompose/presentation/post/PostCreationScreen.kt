package ru.potemkin.orpheusjetpackcompose.presentation.post

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.SpacerWidth
import ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile.PrivacySettingsScreen
import ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile.SettingsItem
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.Grey
import ru.potemkin.orpheusjetpackcompose.ui.theme.LightBlack
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PostCreationScreen(
    creatorInfoItem: CreatorInfoItem,
    onBackPressed: () -> Unit,
    onDonePressed: () -> Unit
) {
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val storagePermissionState = rememberPermissionState(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )
    var postContent by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {

                        androidx.compose.material3.Text(
                            text = "Создать запись",
                            style = MaterialTheme.typography.titleLarge,
                            color = White
                        )

                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            Icons.Default.ArrowBack, contentDescription = "Back",
                            tint = White
                        )
                    }
                },
                actions = {
                    AsyncImage(
                        model = creatorInfoItem.creatorPicture.url,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    SpacerWidth()
                    IconButton(onClick = { onDonePressed() }) {
                        androidx.compose.material.Icon(
                            Icons.Default.Check,
                            contentDescription = "Сохранить",
                            tint = White
                        )
                    }
                },
                backgroundColor = Black
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .background(Black)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(
                            value = postContent,
                            onValueChange = { postContent = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Black,
                                unfocusedIndicatorColor = White,
                                focusedIndicatorColor = White,
                                focusedTextColor = White,
                                cursorColor = White,
                                unfocusedLabelColor = Grey,
                                unfocusedTextColor = Grey
                            ),
                            placeholder = { androidx.compose.material3.Text("Описание записи") },
                        )
                        Spacer(modifier = Modifier.height(64.dp))
                        Surface(
                            modifier = Modifier
                                .clickable {
                                    storagePermissionState.launchPermissionRequest()
                                    if (storagePermissionState.hasPermission) {
                                        try {
                                            singlePhotoPickerLauncher.launch(
                                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                            )
                                        } catch (e: SecurityException) {

                                        }
                                    }
                                }
                                .height(200.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(
                                bottomEndPercent = 10,
                                bottomStartPercent = 10,
                                topEndPercent = 10,
                                topStartPercent = 10
                            ),
                            color = LightBlack
                        ) {
                            AsyncImage(
                                model = selectedImageUri,
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.Crop
                            )
                            if (selectedImageUri == null) {
                                androidx.compose.material.Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Edit",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .alpha(0.5f)
                                        .clip(CircleShape),
                                    tint = White
                                )
                            } else {
                                androidx.compose.material.Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    modifier = Modifier
                                        .size(1.dp)
                                        .alpha(0.5f)
                                        .clip(CircleShape),
                                    tint = White
                                )
                            }

                        }
                    }
                }


            }
        }
    }
}
