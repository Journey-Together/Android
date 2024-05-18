package kr.tekit.lion.daongil.dto.request

data class AreaCodeRequest(
    val numOfRows: String = API_CODES_MAX_NUM_OF_ROWS,
    val mobileOs: String = API_MOBILE_OS,
    val mobileApp: String = APP_NAME,
    val type: String = API_TYPE,
    val areaCode: String = "",
    val serviceKey: String = SERVICE_KEY,
){
    fun toRequestModel(): Map<String, String> {
        return mapOf(
            "numOfRows" to numOfRows,
            "MobileOS" to mobileOs,
            "MobileApp" to mobileApp,
            "areaCode" to areaCode,
            "_type" to type,
            "serviceKey" to serviceKey,
        )
    }

    companion object{
        const val API_CODES_MAX_NUM_OF_ROWS = "31"
        const val API_MOBILE_OS = "AND"
        const val API_TYPE = "json"
        const val APP_NAME = "DaOnGil"
        const val SERVICE_KEY = "t2ivQakqcZ/cvxzekT7Ra9Ja8J1N1lBKu6LqVkijMliEeoD1lLXU0Qei+V9AC8aMbNG+TjVkca70NqFB9akmSg=="
    }
}