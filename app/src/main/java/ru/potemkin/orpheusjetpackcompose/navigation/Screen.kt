package ru.potemkin.orpheusjetpackcompose.navigation

import com.google.gson.Gson
import okio.ByteString.Companion.encode
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.AboutMeItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.RegItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.TypeItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

sealed class Screen(
    val route: String
) {
    object BandProfileScreen : Screen(ROUTE_BAND_PROFILE){

        private const val ROUTE_FOR_ARGS = "band"

        fun getRouteWithArgs(band: BandItem): String {
            val bandJson = Gson().toJson(band)
            return "$ROUTE_FOR_ARGS/${bandJson.encode()}"
        }
    }
    object ChatListScreen : Screen(ROUTE_CHAT_LIST)
    object ChatScreen : Screen(ROUTE_CHAT){

        private const val ROUTE_FOR_ARGS = "chat"

        fun getRouteWithArgs(chat: ChatItem): String {
            val chatJson = Gson().toJson(chat)
            return "$ROUTE_FOR_ARGS/${chatJson.encode()}"
        }
    }
    object CommentsScreen : Screen(ROUTE_COMMENTS){

        private const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(feedPost: PostItem): String {
            val feedPostJson = Gson().toJson(feedPost)
            return "$ROUTE_FOR_ARGS/${feedPostJson.encode()}"
        }
    }
    object LocationScreen : Screen(ROUTE_LOCATION){

        private const val ROUTE_FOR_ARGS = "location"

        fun getRouteWithArgs(location: LocationItem): String {
            val locationJson = Gson().toJson(location)
            return "$ROUTE_FOR_ARGS/${locationJson.encode()}"
        }
    }
    object LoginScreen : Screen(ROUTE_LOGIN)
    object MapScreen : Screen(ROUTE_MAP)
    object NewsFeedScreen : Screen(ROUTE_NEWS_FEED)
    object ProfileScreen : Screen(ROUTE_PROFILE)
    object RegistrationAboutMeScreen : Screen(ROUTE_REGISTRATION_ABOUT_ME){

        private const val ROUTE_FOR_ARGS = "reg_about_me"

        fun getRouteWithArgs(regItem: RegItem): String {
            val argsJson = Gson().toJson(regItem)
            return "$ROUTE_FOR_ARGS/${argsJson.encode()}"
        }
    }
    object RegistrationUserTypeScreen : Screen(ROUTE_REGISTRATION_USER_TYPE){

        private const val ROUTE_FOR_ARGS = "reg_user_type"

        fun getRouteWithArgs(aboutMeItem: AboutMeItem): String {
            val argsJson = Gson().toJson(aboutMeItem)
            return "$ROUTE_FOR_ARGS/${argsJson.encode()}"
        }
    }
    object RegistrationMusicianTypeScreen : Screen(ROUTE_REGISTRATION_MUS){

        private const val ROUTE_FOR_ARGS = "reg_mus_type"

        fun getRouteWithArgs(typeItem: TypeItem): String {
            val argsJson = Gson().toJson(typeItem)
            return "$ROUTE_FOR_ARGS/${argsJson.encode()}"
        }
    }
    object RegistrationAdministratorTypeScreen : Screen(ROUTE_REGISTRATION_ADMN){

        private const val ROUTE_FOR_ARGS = "reg_admin_type"

        fun getRouteWithArgs(typeItem: TypeItem): String {
            val argsJson = Gson().toJson(typeItem)
            return "$ROUTE_FOR_ARGS/${argsJson.encode()}"
        }
    }
    object RegistrationScreen : Screen(ROUTE_REGISTRATION)
    object SearchScreen : Screen(ROUTE_SEARCH)
    object StartScreen : Screen(ROUTE_START)
    object UserProfileScreen : Screen(ROUTE_USER_PROFILE){

        private const val ROUTE_FOR_ARGS = "user"

        fun getRouteWithArgs(user: UserItem): String {
            val userJson = Gson().toJson(user)
            return "$ROUTE_FOR_ARGS/${userJson.encode()}"
        }
    }
    object BandCreationScreen : Screen(ROUTE_BAND_CREATION)
    object BandListScreen : Screen(ROUTE_BAND_LIST)
    object NotificationsScreen : Screen(ROUTE_NOTIFICATIONS)
    object SettingsScreen : Screen(ROUTE_SETTINGS)

    object AuthHomeScreen : Screen(ROUTE_AUTH_HOME)
    object ChatHomeScreen : Screen(ROUTE_CHAT_HOME)
    object MapHomeScreen : Screen(ROUTE_MAP_HOME)
    object ProfileHomeScreen : Screen(ROUTE_PROFILE_HOME)
    object FeedHomeScreen : Screen(ROUTE_FEED_HOME)

    companion object {
        const val KEY_FEED_POST = "feed_post"
        const val KEY_USER = "user"
        const val KEY_BAND = "band"
        const val KEY_CHAT = "chat"
        const val KEY_LOCATION = "location"
        const val KEY_REG_ABOUT_ME = "reg_about_me"
        const val KEY_REG_ADMN = "reg_admn"
        const val KEY_REG_MUS = "reg_mus"
        const val KEY_REG_TYPE = "reg_type"

        const val ROUTE_AUTH_HOME = "auth_home"
        const val ROUTE_BAND_PROFILE = "band_profile"
        const val ROUTE_CHAT_LIST = "chat_list"
        const val ROUTE_CHAT = "chat"
        const val ROUTE_CHAT_HOME = "chat_home"
        const val ROUTE_COMMENTS = "comments"
        const val ROUTE_FEED_HOME = "feed_home"
        const val ROUTE_LOCATION = "location"
        const val ROUTE_LOGIN = "login"
        const val ROUTE_MAP = "map"
        const val ROUTE_MAP_HOME = "map_home"
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_PROFILE = "profile"
        const val ROUTE_PROFILE_HOME = "profile_home"
        const val ROUTE_REGISTRATION = "registration"
        const val ROUTE_REGISTRATION_ABOUT_ME = "registration_about_me"
        const val ROUTE_REGISTRATION_USER_TYPE = "registration_user_type"
        const val ROUTE_REGISTRATION_MUS = "registration_mus"
        const val ROUTE_REGISTRATION_ADMN = "registration_admn"
        const val ROUTE_SEARCH = "search"
        const val ROUTE_START = "start"
        const val ROUTE_USER_PROFILE = "user_profile"
        const val ROUTE_BAND_CREATION = "band_creation"
        const val ROUTE_BAND_LIST = "band_list"
        const val ROUTE_NOTIFICATIONS = "notifications"
        const val ROUTE_SETTINGS = "settings"
    }
}
