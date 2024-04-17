package ru.potemkin.orpheusjetpackcompose.data.repositories

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import ru.potemkin.orpheusjetpackcompose.data.mappers.UsersMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.data.preferences.AuthPreferences
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.repositories.UserRepository
import ru.potemkin.orpheusjetpackcompose.extentions.mergeWith
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
) : UserRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val apiService = ApiFactory.appUserApiService
    private val mapper = UsersMapper()
    private val _userItems = mutableListOf<UserItem>()
    private val userItems: List<UserItem>
        get() = _userItems.toList()

    private val _musicianItems = mutableListOf<MusicianItem>()
    private val musicianItems: List<MusicianItem>
        get() = _musicianItems.toList()

    private var nextFrom: String? = null

    private val _myUser = UserItem(
        "11",
        "pansanek",
        "Sasha Potemkin",
        "12341234",
        "1@gmail.com",
        "Just a drummer, guitarist, bassist etc.",
        UserType.MUSICIAN,
        PhotoUrlItem(
            "111",
            "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
        ),
        PhotoUrlItem(
            "112",
            "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
        ),
        UserSettingsItem(true, true)
    )

    private val refreshedUserListFlow = MutableSharedFlow<List<UserItem>>()
    private val loadedUserListFlow = flow {
        // Проверяем, есть ли уже какие-то посты, и если есть, то их сначала отправляем
        if (userItems.isNotEmpty()) {
            emit(userItems)
        } else {
            addMockData()
            emit(userItems)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }
    private val users: StateFlow<List<UserItem>> = loadedUserListFlow
        .mergeWith(refreshedUserListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = userItems
        )


    private val refreshedMusicianListFlow = MutableSharedFlow<List<MusicianItem>>()
    private val loadedMusicianListFlow = flow {
        if (musicianItems.isNotEmpty()) {
            emit(musicianItems)
        } else {
            addMusicianMockData()
            emit(musicianItems)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }
    private val musicians: StateFlow<List<MusicianItem>> = loadedMusicianListFlow
        .mergeWith(refreshedMusicianListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = musicianItems
        )

    override suspend fun addUserItem(userItem: UserItem) {
        _userItems.add(userItem)
        refreshedUserListFlow.emit(userItems)
    }

    override suspend fun addMusicianItem(musicianItem: MusicianItem) {
        _musicianItems.add(musicianItem)
        refreshedMusicianListFlow.emit(musicianItems)
    }

    override fun getMyUser(): UserItem {
        return UserItem(
            _myUser.id,
            _myUser.login,
            _myUser.name,
            _myUser.password,
            _myUser.email,
            _myUser.about,
            _myUser.user_type,
            _myUser.profile_picture,
            _myUser.background_picture,
            _myUser.settings
        )
    }

    override fun setMyUser(userId: String) {
        Log.d("SETMYUSER","ID: " + userId.toString())
        var userItem = _userItems.find { it.id == userId }
            ?: throw java.lang.RuntimeException("Element with id ${userId} not found")
        Log.d("SETMYUSER","ITEM: " + userItem.toString())
        _myUser.id = userId
        _myUser.login = userItem.login
        _myUser.name = userItem.name
        _myUser.password = userItem.password
        _myUser.email = userItem.email
        _myUser.about = userItem.about
        _myUser.user_type = userItem.user_type
        _myUser.profile_picture = userItem.profile_picture
        _myUser.background_picture = userItem.background_picture
        _myUser.settings = userItem.settings

    }

    override suspend fun deleteUserItem(userItem: UserItem) {
        _userItems.remove(userItem)
        refreshedUserListFlow.emit(userItems)
    }

    override suspend fun editUserItem(userItem: UserItem) {
        setMyUser(userItem.id)
    }


    override fun getUsersList(): StateFlow<List<UserItem>> = users

    override fun getMusiciansList(): StateFlow<List<MusicianItem>> = musicians

    override suspend fun editMusicianItem(musicianItem: MusicianItem) {
        val oldElement = _musicianItems.find {
            it.id == musicianItem.id
        } ?: throw java.lang.RuntimeException("Element with id ${musicianItem.id} not found")
        _musicianItems.remove(oldElement)
        addMusicianItem(musicianItem)
    }

    suspend fun addMockData() {
        addUserItem(
            UserItem(
                "11",
                "pansanek",
                "Sasha Potemkin",
                "12341234",
                "1@gmail.com",
                "Just a drummer, guitarist, bassist etc.",
                UserType.MUSICIAN,
                PhotoUrlItem(
                    "191",
                    "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
                ),
                PhotoUrlItem(
                    "1101",
                    "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
                ),
                UserSettingsItem(true, true)
            )
        )

        addUserItem(
            UserItem(
                "12",
                "noahbadomens",
                "Noah Sebastian",
                "12341234",
                "email@gmail.com",
                "Vocalist",
                UserType.MUSICIAN,
                PhotoUrlItem(
                    "192",
                    "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
                ),
                PhotoUrlItem(
                    "1102",
                    "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                ),
                UserSettingsItem(true, true)
            )
        )

        addUserItem(
            UserItem(
                "13",
                "nicholasriffruff",
                "Nicholas Ruffilo",
                "12341234",
                "3@gmail.com",
                "Guitarist for Bad Omens",
                UserType.MUSICIAN,
                PhotoUrlItem(
                    "193",
                    "https://i.pinimg.com/originals/ed/bc/29/edbc295d751aa4ab31850fd052b7c37a.jpg"
                ),
                PhotoUrlItem(
                    "1103",
                    "https://images.squarespace-cdn.com/content/v1/54a06130e4b0510339b40c5e/1527895061651-7SU9556569GML2IQW2OI/BO-5.jpg"
                ),
                UserSettingsItem(true, true)
            )
        )

        addUserItem(
            UserItem(
                "14",
                "dayseekerrory",
                "Rory Rodriguez",
                "12341234",
                "4@gmail.com",
                "Just a nice guy",
                UserType.MUSICIAN,
                PhotoUrlItem(
                    "194",
                    "https://images.genius.com/4321352a6796b4d618f8324ccdc68181.1000x1000x1.jpg"
                ),
                PhotoUrlItem(
                    "1104",
                    "https://www.bringthenoiseuk.com/wp-content/uploads/Dayseeker-2022-Credit-Amber-Paredes.jpg"
                ),
                UserSettingsItem(true, true)
            )
        )

        addUserItem(
            UserItem(
                "15",
                "jessiecash5",
                "Jessie Cash",
                "12341234",
                "5@gmail.com",
                "Ghost Atlas / Erra",
                UserType.MUSICIAN,
                PhotoUrlItem(
                    "195",
                    "https://thenewfury.com/wp-content/uploads/2017/10/unnamed-1.jpg"
                ),
                PhotoUrlItem(
                    "1105",
                    "https://i.ytimg.com/vi/7VvYYohzaj0/maxresdefault.jpg"
                ),
                UserSettingsItem(true, true)
            )
        )

        addUserItem(
            UserItem(
                "16",
                "almblabalbladh",
                "Hakan Almbladh",
                "12341234",
                "email@gmail.com",
                "Guitarist for Normandie",
                UserType.MUSICIAN,
                PhotoUrlItem(
                    "196",
                    "https://sun6-23.userapi.com/s/v1/if1/Gzwvj0HYoXOEjeEzkx1zmYMRGnRDw387ol_FVX2xcHPijVR0XFMOuWPcZz09cyt32p_ne61G.jpg?size=400x400&quality=96&crop=6,0,1428,1428&ava=1"
                ),
                PhotoUrlItem(
                    "1106",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/0/04/Normandie_-_Bochum_Total_-_160716LEOKR009181510000.jpg/440px-Normandie_-_Bochum_Total_-_160716LEOKR009181510000.jpg"
                ),
                UserSettingsItem(true, true)
            )
        )

        addUserItem(
            UserItem(
                "17",
                "antonfranzon",
                "Anton Franzon",
                "12341234",
                "email@gmail.com",
                "Drummer for Normandie",
                UserType.MUSICIAN,
                PhotoUrlItem(
                    "197",
                    "https://sun1-91.userapi.com/s/v1/ig2/VuGmflKD09SOOd9MeZIZzPqQmdYqbyJbu5VuHJz8ur39YTANcs4FudgMJrmrzKao6_fdj0zO3nUTymhBXrQwaW6P.jpg?size=400x400&quality=95&crop=270,705,873,873&ava=1"
                ),
                PhotoUrlItem(
                    "1107",
                    "https://sun9-80.userapi.com/impf/c855524/v855524135/19eaeb/VfxIoZvQHGA.jpg?size=604x403&quality=96&sign=d9899d7b718557643742be9c124e1e5a&type=album"
                ),
                UserSettingsItem(true, true)
            )
        )

        addUserItem(
            UserItem(
                "18",
                "strander",
                "Philip Strand",
                "12341234",
                "8@gmail.com",
                "Vocalist for Normandie",
                UserType.MUSICIAN,
                PhotoUrlItem(
                    "198",
                    "https://soundgraphics.net/wp/wp-content/uploads/2020/04/Philip-Strand_Picture_square.jpg"
                ),
                PhotoUrlItem(
                    "1108",
                    "https://i.ytimg.com/vi/ZIyPgFwXkEY/maxresdefault.jpg"
                ),
                UserSettingsItem(true, true)
            )
        )

        addUserItem(
            UserItem(
                "19",
                "landontewers",
                "Landon Tewers",
                "12341234",
                "9@gmail.com",
                "Много пою и кричу",
                UserType.MUSICIAN,
                PhotoUrlItem(
                    "199",
                    "https://sun9-25.userapi.com/impf/c840320/v840320259/36208/h5GVeRP9URM.jpg?size=640x640&quality=96&sign=f5307f49e081c58b7cbb3bbb4680efb6&c_uniq_tag=FbHPADgjU38jiYwFHjugwpbBeRJbDbBXfs4fCfTv3rk&type=album"
                ),
                PhotoUrlItem(
                    "1109",
                    "https://i0.wp.com/distortedsoundmag.com/wp-content/uploads/2017/11/TPIY_Dispose_3000px_600dpi_RGB.jpg?w=3000&ssl=1"
                ),
                UserSettingsItem(true, true)
            )
        )

        addUserItem(
            UserItem(
                "110",
                "darkmagician",
                "Kiryll Denisov",
                "12341234",
                "10@gmail.com",
                "А что если...",
                UserType.ADMINISTRATOR,
                PhotoUrlItem(
                    "1910",
                    "https://live.staticflickr.com/5172/5484583701_e29c7aa381_b.jpg"
                ),
                PhotoUrlItem(
                    "11010",
                    "https://cdn.oboi7.com/225be3814aa2e64db6be175d8a197eeb26ea3d58/presmykayushijsya-pugalo-cherep-kid.jpg"
                ),
                UserSettingsItem(true, true)
            )
        )

        addUserItem(
            UserItem(
                "111",
                "pavell",
                "Pavel Litvinov",
                "12341234",
                "11@gmail.com",
                "Administrator for the best Repetition base in Moscow",
                UserType.ADMINISTRATOR,
                PhotoUrlItem(
                    "1911",
                    "https://i.pinimg.com/236x/1a/ec/84/1aec844e8866d63a65d4e9b705211d01.jpg?nii=t"
                ),
                PhotoUrlItem(
                    "1910",
                    "https://wallpapers.com/images/file/winding-road-dark-forest-shsyax3tzax42p9o.jpg"
                ),
                UserSettingsItem(true, true)
            )
        )
    }


    suspend fun addMusicianMockData() {
        addMusicianItem(
            MusicianItem(
                "81",
                UserItem(
                    "11",
                    "pansanek",
                    "Sasha Potemkin",
                    "12341234",
                    "1@gmail.com",
                    "Just a drummer, guitarist, bassist etc.",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "111",
                        "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
                    ),
                    PhotoUrlItem(
                        "112",
                        "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                "Metalcore",
                "Drums"
            )
        )
        addMusicianItem(
            MusicianItem(
                "82",
                UserItem(
                    "12",
                    "noahbadomens",
                    "Noah Sebastian",
                    "12341234",
                    "email@gmail.com",
                    "Vocalist",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "192",
                        "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
                    ),
                    PhotoUrlItem(
                        "1102",
                        "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                "Metalcore",
                "Vocals"
            )
        )

        addMusicianItem(
            MusicianItem(
                "83",
                UserItem(
                    "13",
                    "nicholasriffruff",
                    "Nicholas Ruffilo",
                    "12341234",
                    "3@gmail.com",
                    "Guitarist for Bad Omens",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "193",
                        "https://i.pinimg.com/originals/ed/bc/29/edbc295d751aa4ab31850fd052b7c37a.jpg"
                    ),
                    PhotoUrlItem(
                        "1103",
                        "https://images.squarespace-cdn.com/content/v1/54a06130e4b0510339b40c5e/1527895061651-7SU9556569GML2IQW2OI/BO-5.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                "Metalcore",
                "Guitar"
            )
        )

        addMusicianItem(
            MusicianItem(
                "84",
                UserItem(
                    "14",
                    "dayseekerrory",
                    "Rory Rodriguez",
                    "12341234",
                    "4@gmail.com",
                    "Just a nice guy",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "194",
                        "https://images.genius.com/4321352a6796b4d618f8324ccdc68181.1000x1000x1.jpg"
                    ),
                    PhotoUrlItem(
                        "1104",
                        "https://www.bringthenoiseuk.com/wp-content/uploads/Dayseeker-2022-Credit-Amber-Paredes.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                "Metalcore",
                "Vocals"
            )
        )


        addMusicianItem(
            MusicianItem(
                "85",
                UserItem(
                    "15",
                    "jessiecash5",
                    "Jessie Cash",
                    "12341234",
                    "5@gmail.com",
                    "Ghost Atlas / Erra",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "195",
                        "https://thenewfury.com/wp-content/uploads/2017/10/unnamed-1.jpg"
                    ),
                    PhotoUrlItem(
                        "1105",
                        "https://i.ytimg.com/vi/7VvYYohzaj0/maxresdefault.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                "Metalcore",
                "Vocals"
            )
        )

        addMusicianItem(
            MusicianItem(
                "86",
                UserItem(
                    "16",
                    "almblabalbladh",
                    "Hakan Almbladh",
                    "12341234",
                    "email@gmail.com",
                    "Guitarist for Normandie",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "196",
                        "https://sun6-23.userapi.com/s/v1/if1/Gzwvj0HYoXOEjeEzkx1zmYMRGnRDw387ol_FVX2xcHPijVR0XFMOuWPcZz09cyt32p_ne61G.jpg?size=400x400&quality=96&crop=6,0,1428,1428&ava=1"
                    ),
                    PhotoUrlItem(
                        "1106",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/0/04/Normandie_-_Bochum_Total_-_160716LEOKR009181510000.jpg/440px-Normandie_-_Bochum_Total_-_160716LEOKR009181510000.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                "Metalcore",
                "Guitar"
            )
        )

        addMusicianItem(
            MusicianItem(
                "87",
                UserItem(
                    "17",
                    "antonfranzon",
                    "Anton Franzon",
                    "12341234",
                    "email@gmail.com",
                    "Drummer for Normandie",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "197",
                        "https://sun1-91.userapi.com/s/v1/ig2/VuGmflKD09SOOd9MeZIZzPqQmdYqbyJbu5VuHJz8ur39YTANcs4FudgMJrmrzKao6_fdj0zO3nUTymhBXrQwaW6P.jpg?size=400x400&quality=95&crop=270,705,873,873&ava=1"
                    ),
                    PhotoUrlItem(
                        "1107",
                        "https://sun9-80.userapi.com/impf/c855524/v855524135/19eaeb/VfxIoZvQHGA.jpg?size=604x403&quality=96&sign=d9899d7b718557643742be9c124e1e5a&type=album"
                    ),
                    UserSettingsItem(true, true)
                ),
                "Metalcore",
                "Drums"
            )
        )

        addMusicianItem(
            MusicianItem(
                "88",
                UserItem(
                    "18",
                    "strander",
                    "Philip Strand",
                    "12341234",
                    "8@gmail.com",
                    "Vocalist for Normandie",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "198",
                        "https://soundgraphics.net/wp/wp-content/uploads/2020/04/Philip-Strand_Picture_square.jpg"
                    ),
                    PhotoUrlItem(
                        "1108",
                        "https://i.ytimg.com/vi/ZIyPgFwXkEY/maxresdefault.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                "Metalcore",
                "Vocals"
            )
        )

        addMusicianItem(
            MusicianItem(
                "89",
                UserItem(
                    "19",
                    "landontewers",
                    "Landon Tewers",
                    "12341234",
                    "9@gmail.com",
                    "Много пою и кричу",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "199",
                        "https://sun9-25.userapi.com/impf/c840320/v840320259/36208/h5GVeRP9URM.jpg?size=640x640&quality=96&sign=f5307f49e081c58b7cbb3bbb4680efb6&c_uniq_tag=FbHPADgjU38jiYwFHjugwpbBeRJbDbBXfs4fCfTv3rk&type=album"
                    ),
                    PhotoUrlItem(
                        "1109",
                        "https://i0.wp.com/distortedsoundmag.com/wp-content/uploads/2017/11/TPIY_Dispose_3000px_600dpi_RGB.jpg?w=3000&ssl=1"
                    ),
                    UserSettingsItem(true, true)
                ),
                "Metalcore",
                "Vocals"
            )
        )


    }

    companion object {

        private const val RETRY_TIMEOUT_MILLIS = 5L
    }
}