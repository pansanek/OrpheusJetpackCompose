package ru.potemkin.orpheusjetpackcompose.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.band.bandprofile.BandProfileViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.map.location.LocationViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers.UserProfileViewModel
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@Composable
fun StartUserChatDialog(toUser:UserItem, onDismiss: () -> Unit, onConfirm: () -> Unit,
                    viewModel: UserProfileViewModel
) {
    var message by remember { mutableStateOf("") }
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column() {
                // Отображение изображения
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(toUser.profile_picture.url)
                            .crossfade(true)
                            .build(),
                        contentDescription = toUser.name,
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        contentScale = ContentScale.Crop
                    )
                }
                Column(modifier = Modifier.padding(16.dp)) {
                // Заголовок
                // Текст
                TextEntryModule(
                    textValue = message,
                    onValueChanged = { message = it },
                    hint = ("Сообщение"),
                    modifier = Modifier
                        .fillMaxWidth(),
                    height = 100.dp,
                    cursorColor = Black,
                    description = "Сообщение",
                    singleLine = false,
                    leadingIcon = null,
                    trailingIcon = null,
                    onTrailingIconClick = null,
                    textColor = Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
                    ) {
                        Text("Отмена")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            viewModel.createChat(toUser,message)
                            onConfirm()
                                  },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Black,
                            contentColor = White
                        ),
                    ) {
                        Text("Отправить")
                    }
                }
            }
            }
        }
    }
}

@Composable
fun StartAdminChatDialog(toUser:UserItem, onDismiss: () -> Unit, onConfirm: () -> Unit,
                        viewModel: LocationViewModel
) {
    var message by remember { mutableStateOf("") }
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column() {
                // Отображение изображения
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(toUser.profile_picture.url)
                            .crossfade(true)
                            .build(),
                        contentDescription = toUser.name,
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        contentScale = ContentScale.Crop
                    )
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    TextEntryModule(
                        textValue = message,
                        onValueChanged = { message = it },
                        hint = ("Сообщение"),
                        modifier = Modifier
                            .fillMaxWidth(),
                        height = 100.dp,
                        cursorColor = Black,
                        description = "Сообщение",
                        singleLine = false,
                        leadingIcon = null,
                        trailingIcon = null,
                        onTrailingIconClick = null,
                        textColor = Black
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(
                            onClick = onDismiss,
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
                        ) {
                            Text("Отмена")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                viewModel.createChat(toUser, message)
                                onConfirm()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            ),
                        ) {
                            Text("Отправить")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StartBandChatDialog(band: BandItem, onDismiss: () -> Unit, onConfirm: () -> Unit,
                        viewModel: BandProfileViewModel
) {
    var message by remember { mutableStateOf("") }
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column() {
                // Отображение изображения
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(band.photo.url)
                            .crossfade(true)
                            .build(),
                        contentDescription = band.name,
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        contentScale = ContentScale.Crop
                    )
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    TextEntryModule(
                        textValue = message,
                        onValueChanged = { message = it },
                        hint = ("Сообщение"),
                        modifier = Modifier
                            .fillMaxWidth(),
                        height = 100.dp,
                        cursorColor = Black,
                        description = "Сообщение",
                        singleLine = false,
                        leadingIcon = null,
                        trailingIcon = null,
                        onTrailingIconClick = null,
                        textColor = Black
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(
                            onClick = onDismiss,
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
                        ) {
                            Text("Отмена")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                viewModel.bandChat(message)
                                onConfirm()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            ),
                        ) {
                            Text("Отправить")
                        }
                    }
                }
            }
        }
    }
}