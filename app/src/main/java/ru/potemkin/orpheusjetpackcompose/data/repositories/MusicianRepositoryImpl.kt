package ru.potemkin.orpheusjetpackcompose.data.repositories

import ru.potemkin.orpheusjetpackcompose.data.mappers.PostMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.MusicianMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.MusicianRepository
import javax.inject.Inject

class MusicianRepositoryImpl @Inject constructor(

): MusicianRepository {

    private val apiService = ApiFactory.appMusicianApiService
    private val mapper = MusicianMapper()
    private val _musicianItems = mutableListOf<MusicianItem>()
    private val musicianItems: List<MusicianItem>
        get() = _musicianItems.toList()

    private var nextFrom: String? = null



    override fun addMusicianItem(musicianItem: MusicianItem) {
        _musicianItems.add(musicianItem)
    }
    suspend fun loadMusicians(): List<MusicianItem> {
        val startFrom = nextFrom

        if (startFrom == null && musicianItems.isNotEmpty()) return musicianItems

        val response = if (startFrom == null) {
            apiService.getAllMusicians()
        } else {
            apiService.getAllMusicians()
        }
        val posts = mapper.mapMusicians(response)
        _musicianItems.addAll(posts)
        return musicianItems
    }
    fun getMyMusician():MusicianItem {
        TODO()
//        return _musicianItems.find {
//            it.id ==
//        } ?: throw java.lang.RuntimeException("Element with id $musicianId not found")
    }
    override fun deleteMusicianItem(musicianItem: MusicianItem) {
        _musicianItems.remove(musicianItem)
    }

    override fun editMusicianItem(musicianItem: MusicianItem) {
        val oldElement = getMusicianItem(musicianItem.id)
        _musicianItems.remove(oldElement)
        addMusicianItem(musicianItem)
    }

    override fun getMusicianItem(musicianId: String): MusicianItem {
        return _musicianItems.find {
            it.id == musicianId
        } ?: throw java.lang.RuntimeException("Element with id $musicianId not found")
    }

    override fun getMusiciansList(): List<MusicianItem> {
        return _musicianItems.toList()
    }
}