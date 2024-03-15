package ru.potemkin.orpheusjetpackcompose.data.repositories

import ru.potemkin.orpheusjetpackcompose.data.mappers.PostMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.BandMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.repositories.BandRepository
import javax.inject.Inject

class BandRepositoryImpl @Inject constructor(

): BandRepository {

    private val apiService = ApiFactory.appBandApiService
    private val mapper = BandMapper()
    private val _bandItems = mutableListOf<BandItem>()
    private val bandItems: List<BandItem>
        get() = _bandItems.toList()

    private var nextFrom: String? = null

init {
    addBandItem(BandItem(
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
}
    override fun addBandItem(bandItem: BandItem) {
        _bandItems.add(bandItem)
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
        for (band in _bandItems){
            for (member in band.members) {
                if (member.id == userId) userBands.add(band)
            }
        }
        return userBands
    }
}