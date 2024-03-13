package ru.potemkin.orpheusjetpackcompose.presentation.post

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.ui.theme.*

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    feedPost: PostItem,
    onLikeClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
) {
    Card(
        modifier = modifier
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightBlack,
        ),
        elevation = CardDefaults.cardElevation(
            // Установка высоты карточки
            defaultElevation = 1.dp, // По умолчанию 4.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)

        ) {
            PostHeader(
                feedPost = feedPost,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = feedPost.text, color = White)
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = feedPost.attachment.url,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistics(
                statistics = feedPost.statistics,
                onLikeClickListener = onLikeClickListener,
                onCommentClickListener = onCommentClickListener,
                isFavourite = feedPost.isLiked
            )
        }
    }
}

@Composable
private fun PostHeader(
    feedPost: PostItem,
) {
    var showMenu by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = feedPost.creatorPicture.url,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                ,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = feedPost.creatorName,
                color = White
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = feedPost.date,
                color = White
            )
        }
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopEnd)
        ) {
            IconButton(
                onClick = {
                    showMenu = !showMenu
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = null,
                    tint = White,

                    )
            }
            DropdownMenu(
                containerColor = Black,
                shadowElevation = 16.dp,
                tonalElevation = 16.dp,
                expanded = showMenu,
                onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = null,
                            tint = White
                            )
                    },
                    text = { Text(text = "Редактировать",color = White) },
                    onClick = { }
                )
                DropdownMenuItem(
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = null,
                            tint = Red
                        )
                    },
                    text = { Text(text = "Пожаловаться",color = Red) },
                    onClick = { }
                )
            }
        }
    }
}

@Composable
private fun Statistics(
    statistics: List<StatisticItem>,
    onLikeClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
    isFavourite: Boolean
) {
    Row {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val commentItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                iconResId = R.drawable.ic_comment,
                text = formatStatisticCount(commentItem.count),
                onItemClickListener = {
                    onCommentClickListener(commentItem)
                },
                tint = White
            )
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                iconResId = if (isFavourite) R.drawable.ic_like_set else R.drawable.ic_like,
                text = formatStatisticCount(likesItem.count),
                onItemClickListener = {
                    onLikeClickListener(likesItem)
                },
                tint = if (isFavourite) White else Red
            )
        }
    }
}

private fun formatStatisticCount(count: Int): String {
    return if (count > 100_000) {
        String.format("%sK", (count / 1000))
    } else if (count > 1000) {
        String.format("%.1fK", (count / 1000f))
    } else {
        count.toString()
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException()
}

@Composable
private fun IconWithText(
    iconResId: Int,
    text: String,
    onItemClickListener: (() -> Unit)? = null,
    tint: Color = Color.Gray
) {
    val modifier = if (onItemClickListener == null) {
        Modifier
    } else {
        Modifier.clickable {
            onItemClickListener()
        }
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = tint
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = Color.Gray
        )
    }
}