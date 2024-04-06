package ru.potemkin.orpheusjetpackcompose.data.repositories

import ru.potemkin.orpheusjetpackcompose.data.mappers.BandMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.repositories.BandRepository
import javax.inject.Inject

class BandRepositoryImpl @Inject constructor(

) : BandRepository {

    private val apiService = ApiFactory.appBandApiService
    private val mapper = BandMapper()
    private val _bandItems = mutableListOf<BandItem>()
    private val bandItems: List<BandItem>
        get() = _bandItems.toList()

    private var nextFrom: String? = null

    init {
        addMockData()
    }

    override fun addBandItem(bandItem: BandItem) {
        _bandItems.add(bandItem)
    }

    override fun addBandMemberItem(bandItem: BandItem,myUser:UserItem) {
        _bandItems.remove(bandItem)
        var members = mutableListOf(myUser)
        for (i in bandItem.members) members.add(i)
        bandItem.members = members
        addBandItem(bandItem)
    }

    suspend fun loadBands(): List<BandItem> {
        val startFrom = nextFrom

        if (startFrom == null && bandItems.isNotEmpty()) return bandItems

        val response = if (startFrom == null) {
            apiService.getAllBands()
        } else {
            apiService.getAllBands()
        }
        val posts = mapper.mapBands(response)
        _bandItems.addAll(posts)
        return bandItems
    }

    override fun deleteBandItem(bandItem: BandItem) {
        _bandItems.remove(bandItem)
    }

    override fun editBandItem(bandItem: BandItem) {
        val oldElement = getBandItem(bandItem.id)
        _bandItems.remove(oldElement)
        addBandItem(bandItem)
    }

    override fun getBandItem(bandId: String): BandItem {
        return _bandItems.find {
            it.id == bandId
        } ?: throw java.lang.RuntimeException("Element with id $bandId not found")
    }

    override fun getBandsList(): List<BandItem> {
        return _bandItems.toList()
    }

    override fun getMyUserBands(userId: String): List<BandItem> {
        val userBands = mutableListOf<BandItem>()
        for (band in _bandItems) {
            for (member in band.members) {
                if (member.id == userId) userBands.add(band)
            }
        }
        return userBands
    }

    override fun getUserBands(userId: String): List<BandItem> {
        val userBands = mutableListOf<BandItem>()
        for (band in _bandItems) {
            for (member in band.members) {
                if (member.id == userId) userBands.add(band)
            }
        }
        return userBands
    }


    fun addMockData() {
        addBandItem(
            BandItem(
                "71",
                "Bad Omens",
                listOf(
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
                ),
                "Metalcore",
                PhotoUrlItem(
                    "791",
                    "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                )
            )
        )

        addBandItem(
            BandItem(
                "72",
                "Ghost Atlas",
                listOf(
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
                ),
                "Metalcore",
                PhotoUrlItem(
                    "792",
                    "https://audioveinentertainment.com/wp-content/uploads/2017/11/GhostAtlas-1200x640.jpg"
                )
            )
        )

        addBandItem(
            BandItem(
                "73",
                "The Plot In You",
                listOf(
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
                ),
                "Metalcore",
                PhotoUrlItem(
                    "793",
                    "https://substreammagazine.com/wp-content/uploads/2018/01/Main1-2000x1333.jpg"
                )
            )
        )

        addBandItem(
            BandItem(
                "74",
                "Normandie",
                listOf(
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
                ),
                "Metalcore",
                PhotoUrlItem(
                    "794",
                    "https://www.bringthenoiseuk.com/wp-content/uploads/normandie-band-2022.jpg"
                )
            )
        )
    }
}