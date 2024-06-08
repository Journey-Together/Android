package kr.tekit.lion.daongil.data.dto.remote.request

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.MyPersonalInfo

@JsonClass(generateAdapter = true)
data class MyPersonalInfoRequest (
    val nickname: String,
    val phone: String,
)

fun MyPersonalInfo.toRequestModel() = MyPersonalInfoRequest(
    nickname = nickname,
    phone = phone,
)