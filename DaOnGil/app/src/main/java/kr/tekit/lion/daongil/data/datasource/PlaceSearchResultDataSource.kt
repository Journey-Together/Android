package kr.tekit.lion.daongil.data.datasource


import kr.tekit.lion.daongil.data.dto.remote.response.plan.PlaceSearchResultsResponse
import kr.tekit.lion.daongil.data.network.service.PlanService

class PlaceSearchResultDataSource (
    private val planService: PlanService
){
    suspend fun getPlaceSearchResult(word: String, page: Int, size: Int) : PlaceSearchResultsResponse{
        return planService.getPlaceSearchResults(word, page, size)
    }
}