package ru.potemkin.orpheusjetpackcompose.presentation.screens

//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.State
//import androidx.compose.runtime.collectAsState
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import ru.potemkin.orpheusjetpackcompose.data.states.CommentsScreenState
//import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
//import ru.potemkin.orpheusjetpackcompose.presentation.viewmodels.CommentsViewModel
//import ru.potemkin.orpheusjetpackcompose.presentation.viewmodels.NewsViewModel
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.navigation.NavHostController
//import ru.potemkin.orpheusjetpackcompose.presentation.components.CommentItem
//
//@Composable
//fun CommentsScreen(
//    navHostController: NavHostController,
//    viewModel: CommentsViewModel
//) {
//
//    val screenState = viewModel.screenState.observeAsState(CommentsScreenState.Initial)
//    CommentsScreenContent(
//        screenState = screenState
//    )
//}
//
//@Composable
//private fun CommentsScreenContent(
//    screenState: State<CommentsScreenState>
//) {
//    val currentState = screenState.value
//
//    if (currentState is CommentsScreenState.Comments) {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = {
//                        Text(text = "Комментарии")
//                    },
//                    navigationIcon = {
//                        IconButton(onClick = { onBackPressed() }) {
//                            Icon(
//                                imageVector = Icons.Filled.ArrowBack,
//                                contentDescription = null
//                            )
//                        }
//                    }
//                )
//            }
//        ) { paddingValues ->
//            LazyColumn(
//                modifier = Modifier.padding(paddingValues),
//                contentPadding = PaddingValues(
//                    top = 16.dp,
//                    start = 8.dp,
//                    end = 8.dp,
//                    bottom = 72.dp
//                ),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                items(
//                    items = currentState.comments,
//                    key = { it.commentId }
//                ) { comment ->
//                    CommentItem(comment = comment)
//                }
//            }
//        }
//    }
//}