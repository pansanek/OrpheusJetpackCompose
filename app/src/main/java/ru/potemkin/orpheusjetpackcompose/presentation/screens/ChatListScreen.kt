package ru.potemkin.orpheusjetpackcompose.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import ru.potemkin.orpheusjetpackcompose.R
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.components.SpacerHeight
import ru.potemkin.orpheusjetpackcompose.presentation.components.SpacerWidth
import ru.potemkin.orpheusjetpackcompose.navigation.CHAT_SCREEN
import ru.potemkin.orpheusjetpackcompose.navigation.USER_PROFILE_SCREEN
import ru.potemkin.orpheusjetpackcompose.ui.theme.*
import ru.potemkin.orpheusjetpackcompose.presentation.viewmodels.ChatListViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.viewmodels.ChatViewModel
import ru.potemkin.orpheusjetpackcompose.ui.theme.Black
import ru.potemkin.orpheusjetpackcompose.ui.theme.Green
import ru.potemkin.orpheusjetpackcompose.ui.theme.Turquoise
import ru.potemkin.orpheusjetpackcompose.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatListScreen(
    navHostController: NavHostController,
    chatListViewModel: ChatListViewModel
) {
    Scaffold(
        bottomBar = {
            ru.potemkin.orpheusjetpackcompose.presentation.components.BottomNavigationBar(
                navHostController = navHostController
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Green)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 30.dp)
                ) {
                    CustomHeader()
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color.White, RoundedCornerShape(
                                    topStart = 30.dp, topEnd = 30.dp
                                )
                            )
                    ) {
                        BottomSheetSwipeUp(
                            modifier = Modifier
                                .align(TopCenter)
                                .padding(top = 15.dp)
                        )
                        LazyColumn(
                            modifier = Modifier.padding(bottom = 15.dp, top = 30.dp)
                        ) {
                            items(chatListViewModel.userList, key = { it.id }) {
                                UserEachRow(user = it,navHostController) {
                                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                        "data",
                                        it
                                    )
                                    navHostController.navigate(CHAT_SCREEN)
                                }
                            }
                        }
                    }
                }
            }
        }

    )


}

@Composable
fun CustomHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp)
    ) {
        Header()
    }
}


@Composable
fun BottomSheetSwipeUp(
    modifier: Modifier
) {

    Box(
        modifier = modifier
            .background(
                Turquoise,
                RoundedCornerShape(90.dp)
            )
            .width(90.dp)
            .height(5.dp)

    )
}


@Composable
fun UserEachRow(
    user: UserItem,
    navHostController: NavHostController,
    onClick: () -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .noRippleEffect { onClick() }
            .padding(horizontal = 20.dp, vertical = 5.dp),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {

                    AsyncImage(
                        model = user.icon,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .clickable {
                                navHostController.navigate(USER_PROFILE_SCREEN)
                            },
                        contentDescription = null
                    )
                    SpacerWidth()
                    Column {
                        Text(
                            text = user.name, style = TextStyle(
                                color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold
                            )
                        )
                        SpacerHeight(5.dp)
                        Text(
                            text = stringResource(R.string.okay), style = TextStyle(
                                color = Black, fontSize = 14.sp
                            )
                        )
                    }

                }
                Text(
                    text = stringResource(R.string._12_23_pm), style = TextStyle(
                        color = Black, fontSize = 12.sp
                    )
                )
            }
            SpacerHeight(15.dp)
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = White)
        }
    }

}


@Composable
fun Header() {

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.W300
            )
        ) {
            append(stringResource(R.string.welcome_back))
        }
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        ) {
            append("Alex")
        }
    }

    Text(
        text = annotatedString,
        modifier = Modifier
            .padding(bottom = 32.dp)
    )

}

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.noRippleEffect(onClick: () -> Unit) = composed {
    clickable(
        interactionSource = MutableInteractionSource(),
        indication = null
    ) {
        onClick()
    }
}





