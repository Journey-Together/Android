package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.data.dto.remote.response.areacode.AreaCodeResponse
import kr.tekit.lion.daongil.data.dto.remote.response.searchkeyword.SearchKeywordResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface KorWithService {
    @GET("areaCode1")
    suspend fun getAreaCode(
        @QueryMap params: Map<String, String>
    ): AreaCodeResponse

    @GET("searchKeyword1")
    suspend fun getSearchByKeywordResult(
        @QueryMap params: Map<String, String>
    ): SearchKeywordResponse

}