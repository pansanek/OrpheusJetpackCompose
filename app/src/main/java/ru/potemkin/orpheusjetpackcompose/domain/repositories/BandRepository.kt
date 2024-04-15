package ru.potemkin.orpheusjetpackcompose.domain.repositories

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

interface BandRepository {

    suspend fun addBandItem(bandItem: BandItem)
    suspend fun addBandMemberItem(bandItem: BandItem,myUser: UserItem)
    suspend fun deleteBandItem(bandItem: BandItem)

    suspend fun editBandItem(bandItem: BandItem)

    fun getBandItem(bandId: String): BandItem

    fun getBandsList(): StateFlow<List<BandItem>>

    fun getMyUserBands(userId: String): StateFlow<List<BandItem>>
    fun getUserBands(userId: String): StateFlow<List<BandItem>>

}
