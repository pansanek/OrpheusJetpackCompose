package ru.potemkin.orpheusjetpackcompose.data.repositories

import ru.potemkin.orpheusjetpackcompose.data.mappers.PostMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.BandMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.BandRepository
import javax.inject.Inject

class BandRepositoryImpl @Inject constructor(

): BandRepository {

    private val apiService = ApiFactory.appBandApiService
    private val mapper = BandMapper()
    private val _bandItems = mutableListOf<BandItem>()
    private val bandItems: List<BandItem>
        get() = _bandItems.toList()

    private var nextFrom: String? = null

    private val postApiService = ApiFactory.appPostApiService
    private val postMapper = PostMapper()

    private val _postItems = mutableListOf<PostItem>()
    private val postItems: List<PostItem>
        get() = _postItems.toList()

    private var postNextFrom: String? = null

    suspend fun loadPosts(bandid:String): List<PostItem> {
        val startFrom = postNextFrom

        if (startFrom == null && postItems.isNotEmpty()) return postItems

        val response = if (startFrom == null) {
            postApiService.getCreatorsPosts(bandid)
        } else {
            postApiService.getCreatorsPosts(bandid)
        }
        val posts = postMapper.mapPostList(response)
        _postItems.addAll(posts)
        return postItems
    }
    override fun addBandItem(bandItem: BandItem) {
        _bandItems.add(bandItem)
    }
    suspend fun loadBands(): List<BandItem> {
        val startFrom = nextFrom

        if (startFrom == null && bandItems.isNotEmpty()) return bandItems

        val response = if (startFrom == null) {
            apiService.getAllBands()
        } else {
            apiService.getAllBands()
        }
        val posts = mapper.mapBands(response)
        _bandItems.addAll(posts)
        return bandItems
    }
    fun getMyBand():BandItem {
        TODO()
//        return _bandItems.find {
//            it.id ==
//        } ?: throw java.lang.RuntimeException("Element with id $bandId not found")
    }
    override fun deleteBandItem(bandItem: BandItem) {
        _bandItems.remove(bandItem)
    }

    override fun editBandItem(bandItem: BandItem) {
        val oldElement = getBandItem(bandItem.id)
        _bandItems.remove(oldElement)
        addBandItem(bandItem)
    }

    override fun getBandItem(bandId: String): BandItem {
        return _bandItems.find {
            it.id == bandId
        } ?: throw java.lang.RuntimeException("Element with id $bandId not found")
    }

    override fun getBandsList(): List<BandItem> {
        return _bandItems.toList()
    }
}