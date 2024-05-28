package ru.potemkin.orpheusjetpackcompose.presentation.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.potemkin.orpheusjetpackcompose.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextEntryModule(
    description: String,
    modifier: Modifier = Modifier,
    hint: String,
    leadingIcon: ImageVector?,
    textValue: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textColor: Color,
    cursorColor: Color,
    height: Dp = 60.dp,
    singleLine:Boolean = true,
    onValueChanged: (String) -> Unit,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)?
) {
    Column(
        modifier = modifier
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp)
                .height(height),
            label = { Text(text = description, color = Black) },
            value = textValue,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = White,
                cursorColor = cursorColor,
                focusedIndicatorColor = Black,
                unfocusedIndicatorColor = Black
            ),
            onValueChange = onValueChanged,
            shape = RoundedCornerShape(25.dp),
            singleLine =  singleLine,
            leadingIcon = {
                if(leadingIcon != null){
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = cursorColor
                )}
            },
            trailingIcon = {
                if(trailingIcon != null){
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        tint = cursorColor,
                        modifier = Modifier
                            .clickable {
                                if(onTrailingIconClick != null) onTrailingIconClick()
                            }
                    )
                }
            },
            placeholder = {
                Text(
                    hint,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordEntryModule(
    description: String,
    modifier: Modifier = Modifier,
    hint: String,
    leadingIcon: ImageVector,
    textValue: String,
    keyboardType: KeyboardType = KeyboardType.Ascii,
    textColor: Color,
    cursorColor: Color,
    onValueChanged: (String) -> Unit,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null
) {
    val isPasswordVisible = remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp)
                .height(60.dp),
            label = { Text(text = description, color = Black) },
            value = textValue,
            onValueChange = onValueChanged,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = White,
                cursorColor = cursorColor,
                focusedIndicatorColor = Black, // Пример цвета, замените на ваш
                unfocusedIndicatorColor = Black
            ),
            shape = RoundedCornerShape(25.dp),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = cursorColor
                )
            },
            trailingIcon = {
                if (trailingIcon != null) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        tint = cursorColor,
                        modifier = Modifier.clickable {
                            isPasswordVisible.value = !isPasswordVisible.value
                            onTrailingIconClick?.invoke()
                        }
                    )
                }
            },
            placeholder = { Text(hint, style = MaterialTheme.typography.bodyMedium) },
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = textColor),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
        )
    }
}
@Preview(showBackground = true)
@Composable
fun TextEntryModulePreview() {
    TextEntryModule(
        description = "Логин",
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 0.dp, 10.dp, 5.dp),
        hint = "xd@xd.xd",
        leadingIcon = Icons.Default.Email,
        textValue = "",
        textColor = Black,
        cursorColor = Green,
        onValueChanged = {},
        trailingIcon = Icons.Default.RemoveRedEye,
        onTrailingIconClick = { /*TODO*/ })
}