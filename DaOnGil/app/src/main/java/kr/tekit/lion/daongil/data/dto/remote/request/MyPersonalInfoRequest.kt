package kr.tekit.lion.daongil.data.dto.remote.request

import kr.tekit.lion.daongil.data.dto.remote.base.AdapterProvider.Companion.JsonAdapter
import kr.tekit.lion.daongil.domain.model.PersonalInfo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

data class MyPersonalInfoRequest(
    val nickname: String,
    val phone: String
)

fun PersonalInfo.toRequestBody(): RequestBody {
    return JsonAdapter(MyPersonalInfoRequest::class.java).toJson(
        MyPersonalInfoRequest(
            this.nickname,
            this.phone
        )
    ).toRequestBody("application/json".toMediaTypeOrNull())
}