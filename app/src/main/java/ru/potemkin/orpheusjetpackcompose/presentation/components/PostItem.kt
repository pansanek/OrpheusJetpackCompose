package ru.potemkin.orpheusjetpackcompose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.potemkin.orpheusjetpackcompose.R

@Composable
fun PostItem(index: Int, imageModifier: Modifier = Modifier) {
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
            // Добавляем изображение внутри Column
            Image(
                painter = painterResource(id = R.drawable.sample9), // Замените на реальный идентификатор вашего изображения
                contentDescription = null, // Установите подходящее описание
                modifier = imageModifier
            )

            Text(
                text = "Item $index",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Description for item $index",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}