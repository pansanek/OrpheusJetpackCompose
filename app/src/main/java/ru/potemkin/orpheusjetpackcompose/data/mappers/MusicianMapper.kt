package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.MusicianDto
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem


class MusicianMapper {
    val userMapper = UsersMapper()
    fun mapMusicians(listMusicianDto: List<MusicianDto>): List<MusicianItem> {
        val result = mutableListOf<MusicianItem>()
        for (musicianDto in listMusicianDto) {
            val musicianItem = MusicianItem(
                id = musicianDto.id,
                user = userMapper.mapUser(musicianDto.user),
                genre = musicianDto.genre,
                instrument = musicianDto.instrument
            )
            result.add(musicianItem)
        }
        return result
    }

    fun mapMusician(musicianDto: MusicianDto): MusicianItem {
        return MusicianItem(
            id = musicianDto.id,
            user = userMapper.mapUser(musicianDto.user),
            genre = musicianDto.genre,
            instrument = musicianDto.instrument
        )
    }
}
