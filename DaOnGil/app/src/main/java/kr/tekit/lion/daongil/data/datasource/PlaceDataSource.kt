package kr.tekit.lion.daongil.data.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.tekit.lion.daongil.data.dto.remote.request.SearchByListRequest
import kr.tekit.lion.daongil.data.dto.remote.request.SearchByMapRequest
import kr.tekit.lion.daongil.data.dto.remote.response.detailPlaceGuest.DetailPlaceGuestResponse
import kr.tekit.lion.daongil.data.dto.remote.response.detailplace.DetailPlaceResponse
import kr.tekit.lion.daongil.data.dto.remote.response.mainplace.MainPlaceResponse
import kr.tekit.lion.daongil.data.dto.remote.response.searchplace.list.SearchPlaceResponse
import kr.tekit.lion.daongil.data.dto.remote.response.searchplace.map.MapSearchPlaceResponse
import kr.tekit.lion.daongil.data.dto.remote.response.review.MyPlaceReviewResponse
import kr.tekit.lion.daongil.data.dto.remote.response.placeReview.PlaceReviewResponse
import kr.tekit.lion.daongil.data.network.service.PlaceService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody

class PlaceDataSource(
    private val placeService: PlaceService
) {
    suspend fun getPlaceDetailInfo(placeId: Long): DetailPlaceResponse {
        return placeService.getPlaceDetailInfo(placeId)
    }

    suspend fun searchPlaceByList(request: SearchByListRequest): SearchPlaceResponse {
        return placeService.searchPlaceByList(
            category = request.category,
            size = request.size,
            page = request.page,
            query = request.query,
            disabilityType = request.disabilityType,
            detailFilter = request.detailFilter,
            areaCode = request.areaCode,
            sigunguCode = request.sigunguCode,
            arrange = request.arrange
        )
    }

    fun searchPlaceByMap(request: SearchByMapRequest): Flow<MapSearchPlaceResponse> {
        return flow {
            emit(
                placeService.searchPlaceByMap(
                    category = request.category,
                    minX = request.minX,
                    maxX = request.maxX,
                    minY = request.minY,
                    maxY = request.maxY,
                    disabilityType = request.disabilityType,
                    detailFilter = request.detailFilter,
                    arrange = request.arrange
                )
            )
        }
    }

    suspend fun getPlaceDetailInfoGuest(placeId: Long): DetailPlaceGuestResponse {
        return placeService.getPlaceDetailInfoGuest(placeId)
    }

    suspend fun getPlaceMainInfo(areaCode: String, sigunguCode: String): MainPlaceResponse {
        return placeService.getPlaceMainInfo(areaCode, sigunguCode)
    }
    
    suspend fun getPlaceReviewList(placeId: Long, size: Int, page: Int) : PlaceReviewResponse {
        return placeService.getPlaceReviewList(placeId, size, page)
    }
    
     suspend fun getMyPlaceReview(size: Int, page: Int): MyPlaceReviewResponse {
        return placeService.getMyPlaceReview(size, page)
    }

    suspend fun writePlaceReviewData(placeId: Long, placeReviewReq: RequestBody, images: List<MultipartBody.Part>): ResponseBody {
        return placeService.writePlaceReviewData(placeId, placeReviewReq, images)
    }
}