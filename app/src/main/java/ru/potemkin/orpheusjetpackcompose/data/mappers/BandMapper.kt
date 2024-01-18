package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.BandDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem

class BandMapper {

    fun mapBands(listBandDto: List<BandDto>): List<BandItem> {
        val result = mutableListOf<BandItem>()
        for (bandDto in listBandDto) {
            val bandItem = BandItem(
                id = bandDto.id,
                name = bandDto.name,
                members = bandDto.members,
                genre = bandDto.genre,
                photo = mapPhotoUrlDtoToItem(bandDto.photo)
            )
            result.add(bandItem)
        }
        return result
    }

    fun mapBand(bandDto: BandDto): BandItem {
        return BandItem(
            id = bandDto.id,
            name = bandDto.name,
            members = bandDto.members,
            genre = bandDto.genre,
            photo = mapPhotoUrlDtoToItem(bandDto.photo)
        )
    }

    private fun mapPhotoUrlDtoToItem(photoUrlDto: PhotoUrlDto): PhotoUrlItem {
        return PhotoUrlItem(
            id = photoUrlDto.id,
            url = photoUrlDto.url
        )
    }
}
