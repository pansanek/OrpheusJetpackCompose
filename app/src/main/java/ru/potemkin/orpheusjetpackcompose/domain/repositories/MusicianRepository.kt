package ru.potemkin.orpheusjetpackcompose.domain.repositories

import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem

interface MusicianRepository {

    fun addMusicianItem(musicianItem: MusicianItem)

    fun deleteMusicianItem(musicianItem: MusicianItem)

    fun editMusicianItem(musicianItem: MusicianItem)

    fun getMusicianItem(musicianId: String): MusicianItem

    fun getMusiciansList(): List<MusicianItem>
}