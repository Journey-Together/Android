package kr.tekit.lion.daongil.dto.request

import kr.tekit.lion.daongil.dto.base.ApiConstants.Companion.API_ARRANGE
import kr.tekit.lion.daongil.dto.base.ApiConstants.Companion.API_LIST_YN
import kr.tekit.lion.daongil.dto.base.ApiConstants.Companion.API_MOBILE_OS
import kr.tekit.lion.daongil.dto.base.ApiConstants.Companion.API_RESULT_MAX_NUM_OF_ROWS
import kr.tekit.lion.daongil.dto.base.ApiConstants.Companion.API_TYPE
import kr.tekit.lion.daongil.dto.base.ApiConstants.Companion.APP_NAME
import kr.tekit.lion.daongil.dto.base.ApiConstants.Companion.SERVICE_KEY

data class SearchKeywordRequest(
    val keyword: String
){
    fun toRequestModel(): Map<String, String>{
        return mapOf(
            "numOfRows" to API_RESULT_MAX_NUM_OF_ROWS,
            "MobileOS" to API_MOBILE_OS,
            "MobileApp" to APP_NAME,
            "_type" to API_TYPE,
            "serviceKey" to SERVICE_KEY,
            "listYN" to API_LIST_YN,
            "arrange" to API_ARRANGE,
            "keyword" to keyword
        )

    }
}