package ru.potemkin.orpheusjetpackcompose.navigation

sealed class Screen(
    val route: String
) {

    object BandProfileScreen : Screen(ROUTE_BAND_PROFILE)
    object ChatListScreen : Screen(ROUTE_CHAT_LIST)
    object ChatScreen : Screen(ROUTE_CHAT)
    object CommentsScreen : Screen(ROUTE_COMMENTS)
    object LocationScreen : Screen(ROUTE_LOCATION)
    object LoginScreen : Screen(ROUTE_LOGIN)
    object MapScreen : Screen(ROUTE_MAP)
    object NewsFeedScreen : Screen(ROUTE_NEWS_FEED)
    object ProfileScreen : Screen(ROUTE_PROFILE)
    object RegistrationScreen : Screen(ROUTE_REGISTRATION)
    object SearchScreen : Screen(ROUTE_SEARCH)
    object StartScreen : Screen(ROUTE_START)
    object UserProfileScreen : Screen(ROUTE_USER_PROFILE)
    object BandCreationScreen : Screen(ROUTE_BAND_CREATION)

    private companion object {

        const val ROUTE_BAND_PROFILE = "band_profile"
        const val ROUTE_CHAT_LIST = "chat_list"
        const val ROUTE_CHAT = "chat"
        const val ROUTE_COMMENTS = "comments"
        const val ROUTE_LOCATION = "location"
        const val ROUTE_LOGIN = "login"
        const val ROUTE_MAP = "map"
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_PROFILE = "profile"
        const val ROUTE_REGISTRATION = "registration"
        const val ROUTE_SEARCH = "search"
        const val ROUTE_START = "start"
        const val ROUTE_USER_PROFILE = "user_profile"
        const val ROUTE_BAND_CREATION = "band_creation"
    }
}
