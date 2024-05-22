package kr.tekit.lion.daongil.data.dto.remote.request

import kr.tekit.lion.daongil.data.dto.remote.base.ApiConstants.Companion.API_CODES_MAX_NUM_OF_ROWS
import kr.tekit.lion.daongil.data.dto.remote.base.ApiConstants.Companion.API_MOBILE_OS
import kr.tekit.lion.daongil.data.dto.remote.base.ApiConstants.Companion.API_TYPE
import kr.tekit.lion.daongil.data.dto.remote.base.ApiConstants.Companion.APP_NAME
import kr.tekit.lion.daongil.data.dto.remote.base.ApiConstants.Companion.SERVICE_KEY

data class AreaCodeRequest(
    val areaCode: String,
){
    fun toRequestModel(): Map<String, String>{
        return mapOf(
            "numOfRows" to API_CODES_MAX_NUM_OF_ROWS,
            "MobileOS" to API_MOBILE_OS,
            "MobileApp" to APP_NAME,
            "_type" to API_TYPE,
            "serviceKey" to SERVICE_KEY,
            "areaCode" to areaCode
        )
    }
}