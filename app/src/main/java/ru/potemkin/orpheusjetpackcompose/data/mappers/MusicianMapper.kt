package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.MusicianDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateMusicianRequest
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateUserRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem


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

    fun mapMusicianToRequest(musicianItem: MusicianItem): CreateMusicianRequest {
        return CreateMusicianRequest(
            user = userMapper.mapUserDto(musicianItem.user),
            genre = musicianItem.genre,
            instrument = musicianItem.instrument
        )
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
