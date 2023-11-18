package ru.potemkin.orpheusjetpackcompose.data.repositories

import android.app.Application
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    application: Application
): LocationRepository {

    private val locationList= mutableListOf<LocationItem>()

    private var autoIncrementId =0
    init{
        val locations = listOf(
            LocationItem(
                1,
                "Культ",
                "КУЛЬТ - настоящий храм творчества и оплот музыкальной КУЛЬТуры, созданный музыкантами для музыкантов.",
                "Электрозаводская улица, 21, Москва",
                ru.potemkin.orpheusjetpackcompose.R.drawable.location1,
                "Repbase",
                1
            ),
            LocationItem(
                2,
                "Культт",
                "КУЛЬТN ",
                "Электрозаводская улица, 21, Москва",
                ru.potemkin.orpheusjetpackcompose.R.drawable.location1,
                "Repbase",
                1
            )
        )
        for (location in locations){
            addLocationItem(location)
        }
    }
    override fun addLocationItem(locationItem: LocationItem) {
        if(locationItem.id == LocationItem.UNDEFINED_ID) {
            locationItem.id = autoIncrementId++
        }
        locationList.add(locationItem)
    }

    override fun deleteLocationItem(locationItem: LocationItem) {
        locationList.remove(locationItem)
    }

    override fun editLocationItem(locationItem: LocationItem) {
        val oldElement = getLocationItem(locationItem.id)
        locationList.remove(oldElement)
        addLocationItem(locationItem)
    }

    override fun getLocationItem(locationId: Int): LocationItem {
        return locationList.find {
            it.id == locationId
        } ?: throw java.lang.RuntimeException("Element with id $locationId not found")
    }

    override fun getLocationsList(): List<LocationItem> {
        return locationList.toList()
    }
}