package ru.potemkin.orpheusjetpackcompose.domain.repositories

import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem

interface BandRepository {

    fun addBandItem(bandItem: BandItem)

    fun deleteBandItem(bandItem: BandItem)

    fun editBandItem(bandItem: BandItem)

    fun getBandItem(bandId: String): BandItem

    fun getBandsList(): List<BandItem>
}