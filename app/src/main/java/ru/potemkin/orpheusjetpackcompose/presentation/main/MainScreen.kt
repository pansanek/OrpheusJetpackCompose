package ru.potemkin.orpheusjetpackcompose.presentation.main

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.navigation.AppNavGraph
import ru.potemkin.orpheusjetpackcompose.navigation.rememberNavigationState
import ru.potemkin.orpheusjetpackcompose.presentation.auth.AboutMeScreen
import ru.potemkin.orpheusjetpackcompose.presentation.auth.AdminRegScreen
import ru.potemkin.orpheusjetpackcompose.presentation.auth.LoginScreen
import ru.potemkin.orpheusjetpackcompose.presentation.auth.MusicianRegScreen
import ru.potemkin.orpheusjetpackcompose.presentation.auth.RegistrationScreen
import ru.potemkin.orpheusjetpackcompose.presentation.auth.StartScreen
import ru.potemkin.orpheusjetpackcompose.presentation.auth.UserTypeScreen
import ru.potemkin.orpheusjetpackcompose.presentation.band.bandcreation.BandCreationScreen
import ru.potemkin.orpheusjetpackcompose.presentation.band.bandcreation.BandListScreen
import ru.potemkin.orpheusjetpackcompose.presentation.chat.chatlist.ChatListScreen
import ru.potemkin.orpheusjetpackcompose.presentation.chat.chat.ChatScreen
import ru.potemkin.orpheusjetpackcompose.presentation.map.location.LocationScreen
import ru.potemkin.orpheusjetpackcompose.presentation.map.map.MapScreen
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments.CommentsScreen
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.NewsFeedScreen
import ru.potemkin.orpheusjetpackcompose.presentation.band.bandprofile.BandProfileScreen
import ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile.MyUserProfileScreen

import ru.potemkin.orpheusjetpackcompose.presentation.search.SearchScreen
import ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile.SettingsScreen
import ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers.UserProfileScreen

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
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Gray,
                        modifier = Modifier.background(color = Color.Black)
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
                    },
                    paddingValues = paddingValues
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
                    paddingValues = paddingValues,
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
                    paddingValues = paddingValues,
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    locationItem = locationItem,
                    onUserClickListener = {
                        navigationState.navigateToUser(locationItem.admin)
                    },
                    onChatClickListener = {
                        navigationState.navigateToUserChat(it)
                    },
                    onCommentClickListener = {
                        navigationState.navigateToComments(it)
                    }
                )
            },
            loginScreenContent = {
                LoginScreen(
                    paddingValues = paddingValues,
                    onRegistrationClickListener = {
                        navigationState.navigateToRegistration()
                    },
                    onNextClickListener = {
                        navigationState.navigateToNewsFeed()
                    })
            },
            mapScreenContent = {
                MapScreen(
                    paddingValues = paddingValues,
                    onLocationClickListener = {
                        navigationState.navigateToLocation(it)
                    }
                )
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
                MyUserProfileScreen(
                    paddingValues = paddingValues,
                    onSettingsClickListener = {
                        navigationState.navigateToSettings()
                    },
                    onCommentClickListener = {
                        navigationState.navigateToComments(it)
                    },
                    onBandListClickListener = {
                                              navigationState.navigateToBandList()
//                        navigationState.navigateToBand(
//                            BandItem(
//                            "51bdc118-e76b-4372-8678-6822658cefed",
//                            "Bad Omens",
//                            listOf(
//                                UserItem(
//                                    "51bdc118-e76b-4372-8678-6822658cefed",
//                                    "noahbadomens",
//                                    "Noah Sebastian",
//                                    "12341234",
//                                    "email@gmail.com",
//                                    "Vocalist for Bad Omens",
//                                    UserType.MUSICIAN,
//                                    PhotoUrlItem(
//                                        "b59ae42e-8859-441a-9a3a-2fca1b784de3",
//                                        "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
//                                    ),
//                                    PhotoUrlItem(
//                                        "b59ae42e-8859-441a-9a3a-2fca1b784de4",
//                                        "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
//                                    ),
//                                    UserSettingsItem(true, true)
//                                ),
//                                UserItem(
//                                    "51bdc118-e76b-4372-8678-6822658cefed",
//                                    "pansanek",
//                                    "Sasha",
//                                    "12341234",
//                                    "email@gmail.com",
//                                    "Hehe",
//                                    UserType.MUSICIAN,
//                                    PhotoUrlItem(
//                                        "b59ae42e-8859-441a-9a3a-2fca1b784de3",
//                                        "https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"
//                                    ),
//                                    PhotoUrlItem(
//                                        "b59ae42e-8859-441a-9a3a-2fca1b784de4",
//                                        "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
//                                    ),
//                                    UserSettingsItem(true, true)
//                                )
//                            ),
//                            "Metalcore",
//                            PhotoUrlItem(
//                                "b59ae42e-8859-441a-9a3a-2fca1b784de4",
//                                "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
//                            )
//                        )
//                        )
                    },
                    onLocationClickListener = {
                        navigationState.navigateToLocation(it)
                    },
                    onSearchClickListener = {navigationState.navigateToSearch()}
                )
            },
            registrationScreenContent = {
                RegistrationScreen(
                    paddingValues = paddingValues,
                    onLoginClickListener = {
                        navigationState.navigateToLogin()
                    },
                    onNextClickListener ={
                        navigationState.navigateToAboutMeScreen(it)
                    }
                    )
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
                    paddingValues = paddingValues,
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    userItem = userItem,
                    onCommentClickListener = {
                        navigationState.navigateToComments(it)
                    },
                    onBandClickListener = {
                        navigationState.navigateToBand(
                            BandItem(
                                "51bdc118-e76b-4372-8678-6822658cefed",
                                "Bad Omens",
                                listOf(
                                    UserItem(
                                        "51bdc118-e76b-4372-8678-6822658cefed",
                                        "noahbadomens",
                                        "Noah Sebastian",
                                        "12341234",
                                        "email@gmail.com",
                                        "Vocalist for Bad Omens",
                                        UserType.MUSICIAN,
                                        PhotoUrlItem(
                                            "b59ae42e-8859-441a-9a3a-2fca1b784de3",
                                            "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
                                        ),
                                        PhotoUrlItem(
                                            "b59ae42e-8859-441a-9a3a-2fca1b784de4",
                                            "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                                        ),
                                        UserSettingsItem(true, true)
                                    ),
                                    UserItem(
                                        "51bdc118-e76b-4372-8678-6822658cefed",
                                        "pansanek",
                                        "Sasha",
                                        "12341234",
                                        "email@gmail.com",
                                        "Hehe",
                                        UserType.MUSICIAN,
                                        PhotoUrlItem(
                                            "b59ae42e-8859-441a-9a3a-2fca1b784de3",
                                            "https://images6.fanpop.com/image/photos/38800000/-Matt-Nicholls-Upset-Magazine-Portrait-bring-me-the-horizon-38883120-1500-2250.jpg"
                                        ),
                                        PhotoUrlItem(
                                            "b59ae42e-8859-441a-9a3a-2fca1b784de4",
                                            "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
                                        ),
                                        UserSettingsItem(true, true)
                                    )
                                ),
                                "Metalcore",
                                PhotoUrlItem(
                                    "b59ae42e-8859-441a-9a3a-2fca1b784de4",
                                    "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                                )
                            )
                        )
                    },
                    onLocationClickListener = {
                        navigationState.navigateToLocation(it)
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
                    },
                    onBandClickListener = {
                        navigationState.navigateToBand(it)
                    }
                )
            },
            registrationAboutMeScreenContent = { regItem ->
                AboutMeScreen(
                    regItem = regItem,
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    onNextClickListener = {
                        navigationState.navigateToUserTypeScreen(it)
                    }
                )
            },
            registrationUserTypeScreenContent = { aboutMeItem ->
                UserTypeScreen(
                    aboutMeItem =aboutMeItem,
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    onAdminClickListener = {
                        navigationState.navigateToAdministratorTypeScreen(it)
                    },
                    onMusicianClickListener = {
                        navigationState.navigateToMusicianTypeScreen(it)
                    }
                )
            },
            registrationAdministratorTypeScreenContent = { typeItem ->
                AdminRegScreen(
                    typeItem = typeItem,
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    onNextClickListener = {
                        navigationState.navigateToNewsFeed()
                    }
                )
            },
            registrationMusicianTypeScreenContent = { typeItem ->
                MusicianRegScreen(
                    typeItem = typeItem,
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    onNextClickListener = {
                        navigationState.navigateToNewsFeed()
                    }
                )
            }

        )

    }
}

