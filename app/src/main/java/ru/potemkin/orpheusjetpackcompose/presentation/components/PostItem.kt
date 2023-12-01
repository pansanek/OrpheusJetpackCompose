package ru.potemkin.orpheusjetpackcompose.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import ru.potemkin.orpheusjetpackcompose.R

import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.navigation.LOCATION_SCREEN
import ru.potemkin.orpheusjetpackcompose.presentation.screens.DetailsDialog
import ru.potemkin.orpheusjetpackcompose.presentation.screens.LocationItem
import ru.potemkin.orpheusjetpackcompose.presentation.viewmodels.NewsViewModel

@Composable
fun PostItem(postItem: PostItem, viewModel: NewsViewModel) {

    val viewState by viewModel.state

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                // Добавляем аватарку пользователя
                AsyncImage(
                    model = postItem.user.icon,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = postItem.user.login,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp)) // Промежуток между аватаркой и контентом

            // Добавляем изображение
            AsyncImage(
                model = postItem.attachments[0],
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(4.dp)) // Промежуток между изображением и текстом

            Text(
                text = postItem.text,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp)) // Промежуток между текстом и кнопками

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                // Кнопка лайка
                IconButton(
                    onClick = { /* Обработчик нажатия на кнопку лайка */ },
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Лайк")
                }
                // Кнопка комментария
                IconButton(
                    onClick = {
                        viewModel.selectPost(postItem)
                        viewModel.openCommentDialog()
                    },
                ) {
                    Icon(imageVector = Icons.Default.Comment, contentDescription = "Комментарий")
                }
            }
            Text(
                text = "Нравится: ${postItem.likes} ",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(8.dp)
            )
            if (postItem == viewState.selectedPost) {
                CommentDialog(
                    onClose = {
                        viewModel.clearSelectedPost()
                        viewModel.closeCommentDialog()
                    },
                    isOpen = viewState.isCommentDialogOpen,
                    comments = postItem.comments // Передайте реальный список комментариев
                )
            }
        }
    }
}

@Composable
private fun CommentDialog(
    comments: List<CommentItem>,
    isOpen: Boolean,
    onClose: () -> Unit
) {
    if (isOpen) {
        val shape = MaterialTheme.shapes.medium

        Dialog(
            onDismissRequest = { onClose() },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {

            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface, shape = shape)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Комментарии",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { onClose() },
                            modifier = Modifier
                                .size(32.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                    LazyColumn {
                        items(comments) {
                            CommentItem(
                                it
                            )
                        }
                    }
                }
            }
        }
    }

}


