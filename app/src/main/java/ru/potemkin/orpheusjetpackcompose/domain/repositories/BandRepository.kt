package ru.potemkin.orpheusjetpackcompose.domain.repositories

import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

interface BandRepository {

    fun addBandItem(bandItem: BandItem)
    fun addBandMemberItem(bandItem: BandItem,myUser: UserItem)
    fun deleteBandItem(bandItem: BandItem)

    fun editBandItem(bandItem: BandItem)

    fun getBandItem(bandId: String): BandItem

    fun getBandsList(): List<BandItem>

    fun getMyUserBands(userId: String): List<BandItem>
    fun getUserBands(userId: String): List<BandItem>

}
