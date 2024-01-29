package ru.potemkin.orpheusjetpackcompose.presentation.main

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.potemkin.orpheusjetpackcompose.navigation.AppNavGraph
import ru.potemkin.orpheusjetpackcompose.navigation.rememberNavigationState
import ru.potemkin.orpheusjetpackcompose.presentation.auth.LoginScreen
import ru.potemkin.orpheusjetpackcompose.presentation.auth.RegistrationScreen
import ru.potemkin.orpheusjetpackcompose.presentation.auth.StartScreen
import ru.potemkin.orpheusjetpackcompose.presentation.band.BandCreationScreen
import ru.potemkin.orpheusjetpackcompose.presentation.band.BandListScreen
import ru.potemkin.orpheusjetpackcompose.presentation.chat.ChatListScreen
import ru.potemkin.orpheusjetpackcompose.presentation.chat.ChatScreen
import ru.potemkin.orpheusjetpackcompose.presentation.map.LocationScreen
import ru.potemkin.orpheusjetpackcompose.presentation.map.MapScreen
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments.CommentsScreen
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.NewsFeedScreen
import ru.potemkin.orpheusjetpackcompose.presentation.band.BandProfileScreen
import ru.potemkin.orpheusjetpackcompose.presentation.profile.NotificationScreen
import ru.potemkin.orpheusjetpackcompose.presentation.profile.ProfileScreen
import ru.potemkin.orpheusjetpackcompose.presentation.profile.SearchScreen
import ru.potemkin.orpheusjetpackcompose.presentation.profile.SettingsScreen
import ru.potemkin.orpheusjetpackcompose.presentation.profile.UserProfileScreen

@Composable
fun MainScreen(viewModelFactory: ViewModelFactory) {
    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                val items = listOf(
                    NavigationItem.NewsFeed,
                    NavigationItem.Map,
                    NavigationItem.Chat,
                    NavigationItem.Profile
                )
                items.forEach { item ->

                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    BottomNavigationItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            bandProfileScreenContent = { BandProfileScreen() },
            chatListScreenContent ={ ChatListScreen(
                paddingValues = paddingValues,
                onChatClickListener = {
                    navigationState.navigateToComments(it)
                } )}
            ,
            chatScreenContent ={ ChatScreen(navHostController = , chatViewModel = ) },
            commentsScreenContent ={ feedPost ->
                CommentsScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    feedPost = feedPost
                )
            },
            locationScreenContent ={ LocationScreen() },
            loginScreenContent ={ LoginScreen(navHostController = ) },
            mapScreenContent ={ MapScreen(navHostController = , mapViewModel = ) },
            newsFeedScreenContent ={ NewsFeedScreen(
                paddingValues = paddingValues,
                onCommentClickListener = {
                    navigationState.navigateToComments(it)
                }
            )},
            profileScreenContent ={ ProfileScreen(navHostController = , newsViewModel = ) },
            registrationScreenContent ={ RegistrationScreen() },
            searchScreenContent ={ SearchScreen() },
            startScreenContent ={ StartScreen(navHostController = ) },
            userProfileScreenContent ={ UserProfileScreen(navHostController = , newsViewModel = ) },
            bandCreationScreenContent ={ BandCreationScreen() },
            notificationsScreenContent ={ NotificationScreen() },
            settingsScreenContent = { SettingsScreen() },
            bandListScreenContent = { BandListScreen()}

        )
        /*
        Вроде бы сделал навигацию постов и комментариев (переделал конструкторы но без DI)
        Осталось разобраться с навигацией остальных экранов и вообще с остальными экранами
        Потом доделать все для чата(item,repo и тд)
        И на серваке с чатами разобраться
        УРОК 35 sumin
        */
    }
}

