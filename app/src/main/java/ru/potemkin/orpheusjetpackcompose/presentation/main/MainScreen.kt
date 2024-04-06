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
import ru.potemkin.orpheusjetpackcompose.presentation.changeprofile.ChangeBandProfileScreen
import ru.potemkin.orpheusjetpackcompose.presentation.changeprofile.ChangeLocationProfileScreen
import ru.potemkin.orpheusjetpackcompose.presentation.changeprofile.ChangeUserProfileScreen
import ru.potemkin.orpheusjetpackcompose.presentation.post.PostCreationScreen
import ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile.MyUserProfileScreen

import ru.potemkin.orpheusjetpackcompose.presentation.search.SearchScreen
import ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile.SettingsScreen
import ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers.UserProfileScreen

@Composable
fun MainScreen() {
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
                    paddingValues = paddingValues,
                    onChangeProfileClick = {
                        navigationState.navigateToChangeBandProfileScreen(it)
                    },
                    onPostCreateClickListener={
                        navigationState.navigateToPostCreationScreen(it)
                    },
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
                    postItem = feedPost,
                    paddingValues = paddingValues
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
                    },
                    onChangeProfileClick = {
                        navigationState.navigateToChangeLocationProfileScreen(it)
                    },
                    onPostCreateClickListener={
                        navigationState.navigateToPostCreationScreen(it)
                    },
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
                    },
                    onPostCreateClickListener={
                        navigationState.navigateToPostCreationScreen(it)
                    },
                    onLocationClickListener = {
                        navigationState.navigateToLocation(it)
                    },
                    onUserClickListener = {
                        navigationState.navigateToUser(it)
                    },
                    onBandClickListener = {
                        navigationState.navigateToBand(
                           it
                        )
                    }
                )
            },
            profileScreenContent = {
                MyUserProfileScreen(
                    paddingValues = paddingValues,
                    onSettingsClickListener = {
                        navigationState.navigateToSettings(it)
                    },
                    onCommentClickListener = {
                        navigationState.navigateToComments(it)
                    },
                    onBandListClickListener = {
                                              navigationState.navigateToBandList()
                    },
                    onLocationClickListener = {
                        navigationState.navigateToLocation(it)
                    },
                    onSearchClickListener = {navigationState.navigateToSearch()},
                    onPostCreateClickListener={
                        navigationState.navigateToPostCreationScreen(it)
                    }
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
                        navigationState.navigateToBand(it)
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
                    userItem = it,
                    onChangeProfileClick = { navigationState.navigateToChangeUserProfileScreen(it) }
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
            },
            changeUserProfileScreenContent =
            {
                ChangeUserProfileScreen(it,{ navigationState.navHostController.popBackStack()})
            },
            changeBandProfileScreenContent = {bandItem ->
                ChangeBandProfileScreen(
                    band = bandItem,
                    {navigationState.navHostController.popBackStack()}
                )
            },
            changeLocationProfileScreenContent = {locationItem ->
                ChangeLocationProfileScreen(
                    location = locationItem,
                    {navigationState.navHostController.popBackStack()}
                )
            }
            ,
            postCreationScreenContent = {
                creatorInfoItem ->
                PostCreationScreen(
                    creatorInfoItem,
                    onBackPressed = { navigationState.navHostController.popBackStack() },
                    onDonePressed = {navigationState.navHostController.popBackStack()}
                )
            }

        )

    }
}

