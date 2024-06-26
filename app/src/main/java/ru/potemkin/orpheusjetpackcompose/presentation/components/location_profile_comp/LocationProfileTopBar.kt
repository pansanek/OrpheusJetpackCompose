package ru.potemkin.orpheusjetpackcompose.presentation.components.location_profile_comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.my_user_profile_comp.DrawerButton
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@Composable
fun LocationProfileTopBar(
    onBackPressed: () -> Unit,
    locationItem: LocationItem,
    currentUserIsAdmin: Boolean,
    onChangeProfileClick: (LocationItem)->Unit,

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = locationItem.name, color = White)
                    Spacer(modifier = Modifier.weight(1f)) // Занимаем всю доступную ширину
                    if(currentUserIsAdmin) EditButton(locationItem,{onChangeProfileClick(locationItem)})
                }
            },
            navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Назад"
                        , tint = White
                    )
                }
            },
            backgroundColor = Black
        )
    }
}


@Composable
fun EditButton(locationItem: LocationItem,onChangeProfileClick: (LocationItem)->Unit) {
    IconButton(
        onClick = { onChangeProfileClick(locationItem) },
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Редактировать",
            tint = White
        )
    }
}
