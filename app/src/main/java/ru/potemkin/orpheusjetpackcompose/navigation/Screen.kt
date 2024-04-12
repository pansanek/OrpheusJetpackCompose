package ru.potemkin.orpheusjetpackcompose.navigation

import android.net.Uri
import android.util.Log
import com.google.gson.Gson
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.AboutMeItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.RegItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems.TypeItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorInfoItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

sealed class Screen(
    val route: String
) {
    object BandProfileScreen : Screen(ROUTE_BAND_PROFILE){

        private const val ROUTE_FOR_ARGS = "band_profile"

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

        private const val ROUTE_FOR_ARGS = "reg_type"

        fun getRouteWithArgs(aboutMeItem: AboutMeItem): String {
            val argsJson = Gson().toJson(aboutMeItem)
            return "$ROUTE_FOR_ARGS/${argsJson.encode()}"
        }
    }
    object RegistrationMusicianTypeScreen : Screen(ROUTE_REGISTRATION_MUS){

        private const val ROUTE_FOR_ARGS = "reg_mus"

        fun getRouteWithArgs(typeItem: TypeItem): String {
            val argsJson = Gson().toJson(typeItem)
            return "$ROUTE_FOR_ARGS/${argsJson.encode()}"
        }
    }
    object RegistrationAdministratorTypeScreen : Screen(ROUTE_REGISTRATION_ADMN){

        private const val ROUTE_FOR_ARGS = "reg_admn"

        fun getRouteWithArgs(typeItem: TypeItem): String {
            val argsJson = Gson().toJson(typeItem)
            return "$ROUTE_FOR_ARGS/${argsJson.encode()}"
        }
    }
    object RegistrationScreen : Screen(ROUTE_REGISTRATION)
    object SearchScreen : Screen(ROUTE_SEARCH)
    object StartScreen : Screen(ROUTE_START)
    object UserProfileScreen : Screen(ROUTE_USER_PROFILE){

        private const val ROUTE_FOR_ARGS = "user_profile"

        fun getRouteWithArgs(user: UserItem): String {
            val userJson = Gson().toJson(user)
            return "$ROUTE_FOR_ARGS/${userJson.encode()}"
        }
    }
    object BandCreationScreen : Screen(ROUTE_BAND_CREATION)
    object BandListScreen : Screen(ROUTE_BAND_LIST)
    object ChangeUserProfileScreen : Screen(ROUTE_CHANGE_USER_PROFILE){
        private const val ROUTE_FOR_ARGS = "change_user_profile"

        fun getRouteWithArgs(user: UserItem): String {
            val userJson = Gson().toJson(user)
            return "$ROUTE_FOR_ARGS/${userJson.encode()}"
        }
    }
    object ChangeLocationProfileScreen : Screen(ROUTE_CHANGE_LOCATION_PROFILE){
        private const val ROUTE_FOR_ARGS = "change_location_profile"

        fun getRouteWithArgs(location: LocationItem): String {
            val locationJson = Gson().toJson(location)
            return "$ROUTE_FOR_ARGS/${locationJson.encode()}"
        }
    }
    object ChangeBandProfileScreen : Screen(ROUTE_CHANGE_BAND_PROFILE){
        private const val ROUTE_FOR_ARGS = "change_band_profile"

        fun getRouteWithArgs(band: BandItem): String {
            val bandJson = Gson().toJson(band)
            return "$ROUTE_FOR_ARGS/${bandJson.encode()}"
        }
    }
    object SettingsScreen : Screen(ROUTE_SETTINGS){
        private const val ROUTE_FOR_ARGS = "settings"

        fun getRouteWithArgs(user: UserItem): String {
            val userJson = Gson().toJson(user)
            return "$ROUTE_FOR_ARGS/${userJson.encode()}"
        }
    }
    object PostCreationScreen : Screen(ROUTE_CREATE_POST){

        private const val ROUTE_FOR_ARGS = "create_post"

        fun getRouteWithArgs(creatorInfoItem: CreatorInfoItem): String {
            val creatorInfoItemJson = Gson().toJson(creatorInfoItem)
            return "$ROUTE_FOR_ARGS/${creatorInfoItemJson.encode()}"
        }
    }
    object MainScreen : Screen(ROUTE_MAIN)
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
        const val KEY_SETTINGS = "settings"
        const val KEY_CHANGE_USER_PROFILE = "change_user"
        const val KEY_CHANGE_BAND_PROFILE = "change_band"
        const val KEY_CHANGE_LOCATION_PROFILE = "change_location"
        const val KEY_CREATE_POST = "create_post"

        const val ROUTE_AUTH_HOME = "auth_home"
        const val ROUTE_BAND_PROFILE = "band_profile/{$KEY_BAND}"
        const val ROUTE_CHAT_LIST = "chat_list"
        const val ROUTE_CHAT = "chat/{$KEY_CHAT}"
        const val ROUTE_CHAT_HOME = "chat_home"
        const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST}"
        const val ROUTE_FEED_HOME = "feed_home"
        const val ROUTE_LOCATION = "location/{$KEY_LOCATION}"
        const val ROUTE_LOGIN = "login"
        const val ROUTE_MAP = "map"
        const val ROUTE_MAP_HOME = "map_home"
        const val ROUTE_MAIN = "main"
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_PROFILE = "profile"
        const val ROUTE_PROFILE_HOME = "profile_home"
        const val ROUTE_REGISTRATION = "registration"
        const val ROUTE_REGISTRATION_ABOUT_ME = "reg_about_me/{$KEY_REG_ABOUT_ME}"
        const val ROUTE_REGISTRATION_USER_TYPE = "reg_type/{$KEY_REG_TYPE}"
        const val ROUTE_REGISTRATION_MUS = "reg_mus/{$KEY_REG_MUS}"
        const val ROUTE_REGISTRATION_ADMN = "reg_admn/{$KEY_REG_ADMN}"
        const val ROUTE_SEARCH = "search"
        const val ROUTE_START = "start"
        const val ROUTE_USER_PROFILE = "user_profile/{$KEY_USER}"
        const val ROUTE_BAND_CREATION = "band_creation"
        const val ROUTE_BAND_LIST = "band_list"
        const val ROUTE_SETTINGS = "settings/{$KEY_SETTINGS}"
        const val ROUTE_CHANGE_USER_PROFILE = "change_user_profile/{$KEY_CHANGE_USER_PROFILE}"
        const val ROUTE_CHANGE_BAND_PROFILE = "change_band_profile/{$KEY_CHANGE_BAND_PROFILE}"
        const val ROUTE_CHANGE_LOCATION_PROFILE = "change_location_profile/{$KEY_CHANGE_LOCATION_PROFILE}"
        const val ROUTE_CREATE_POST = "create_post/{$KEY_CREATE_POST}"
    }
}
fun String.encode(): String {
    return Uri.encode(this)
}