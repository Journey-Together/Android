package kr.tekit.lion.daongil.dto.request

data class AreaCodeRequest(
    val pageNo: String,
    val mobileOs: String = "AND",
    val mobileApp: String = "DaOnGil",
    val type: String = "json",
    val serviceKey: String,
){
    fun toRequestModel(): Map<String, String> {
        return mapOf(
            "pageNo" to pageNo,
            "MobileOS" to mobileOs,
            "MobileApp" to mobileApp,
            "_type" to type,
            "serviceKey" to serviceKey,
        )
    }
}