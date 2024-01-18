package ru.potemkin.orpheusjetpackcompose.data.mappers

import android.util.Log
import ru.potemkin.orpheusjetpackcompose.data.model.AdministratorDto
import ru.potemkin.orpheusjetpackcompose.data.model.CommentDto
import ru.potemkin.orpheusjetpackcompose.data.model.MusicianDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.PostDto
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MusicianMapper{

    fun mapMusicians(listMusicianDto: List<MusicianDto>): List<MusicianItem> {
        val result = mutableListOf<MusicianItem>()
        for (musicianDto in listMusicianDto) {
            val musicianItem = MusicianItem(
                id = musicianDto.id,
                userId = musicianDto.userId,
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
            userId = musicianDto.userId,
            genre = musicianDto.genre,
            instrument = musicianDto.instrument
        )
    }
}
