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
            bandProfileScreenContent = { bandItem ->
                BandProfileScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    bandItem = bandItem,
                    onCommentClickListener = {
                        navigationState.navigateToComments(it)
                    },
                    onUserClickListener = {
                        navigationState.navigateToUser(it)
                    }
                )
            },
            chatListScreenContent = {
                ChatListScreen(
                    paddingValues = paddingValues,
                    onChatClickListener = {
                        navigationState.navigateToChat(it)
                    },
                    onUserClickListener = {
                        navigationState.navigateToUser(it)
                    })
            },
            chatScreenContent = { chatItem ->
                ChatScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    chatItem = chatItem,
                    onUserClickListener = {
                        navigationState.navigateToUser(it)
                    }
                )
            },
            commentsScreenContent = { feedPost ->
                CommentsScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    feedPost = feedPost,
                )
            },
            locationScreenContent = { locationItem ->
                LocationScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    locationItem = locationItem,
                    onUserClickListener = {
                        navigationState.navigateToUser(locationItem.admin.user)
                    },
                    onChatClickListener = {
                        navigationState.navigateToUserChat(it)
                    }
                )
            },
            loginScreenContent = {
                LoginScreen(
                    paddingValues = paddingValues,
                    onRegistrationClickListener = {
                        navigationState.navigateToRegistration()
                    })
            },
            mapScreenContent = {
                MapScreen(
                    paddingValues = paddingValues,
                    onLocationClickListener = {
                        navigationState.navigateToLocation(it)
                    })
            },
            newsFeedScreenContent = {
                NewsFeedScreen(
                    paddingValues = paddingValues,
                    onCommentClickListener = {
                        navigationState.navigateToComments(it)
                    }
                )
            },
            profileScreenContent = {
                ProfileScreen(
                    paddingValues = paddingValues,
                    onSettingsClickListener = {
                        navigationState.navigateToSettings()
                    },
                    onCommentClickListener = {
                        navigationState.navigateToComments(it)
                    },
                    onBandListClickListener = {
                        navigationState.navigateToBandList()
                    })
            },
            registrationScreenContent = {
                RegistrationScreen(
                    paddingValues = paddingValues,
                    onLoginClickListener = {
                        navigationState.navigateToLogin()
                    })
            },
            searchScreenContent = {
                SearchScreen(
                    paddingValues = paddingValues,
                    onUserClickListener = {
                        navigationState.navigateToUser(it)
                    },
                    onBandClickListener = {
                        navigationState.navigateToBand(it)
                    },
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                )
            },
            startScreenContent = {
                StartScreen(
                    onAuthClickListener = {
                        navigationState.navigateToLogin()
                    },
                )
            },
            userProfileScreenContent = { userItem ->
                UserProfileScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    userItem = userItem,
                    onCommentClickListener = {
                        navigationState.navigateToComments(it)
                    }
                )
            },
            bandCreationScreenContent = {
                BandCreationScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                )
            },
            notificationsScreenContent = {
                NotificationScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                )
            },
            settingsScreenContent = {
                SettingsScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                )
            },
            bandListScreenContent = {
                BandListScreen(
                    onBackPressed = {
                    navigationState.navHostController.popBackStack()
                },
                    onBandCreationClickListener = {
                    navigationState.navigateToBandCreation()
                })
            }

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

