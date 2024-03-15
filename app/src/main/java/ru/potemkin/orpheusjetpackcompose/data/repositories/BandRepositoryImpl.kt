package ru.potemkin.orpheusjetpackcompose.data.repositories

import ru.potemkin.orpheusjetpackcompose.data.mappers.PostMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.BandMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
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