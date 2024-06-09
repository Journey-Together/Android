package kr.tekit.lion.daongil.data.dto.remote.request

import kr.tekit.lion.daongil.data.dto.remote.base.AdapterProvider.Companion.JsonAdapter
import kr.tekit.lion.daongil.domain.model.IceInfo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

data class MyIceInfoRequest (
    val bloodType: String,
    val birth: String,
    val disease: String,
    val allergy: String,
    val medication: String,
    val part1_rel: String,
    val part1_phone: String,
    val part2_rel: String,
    val part2_phone: String
)

fun IceInfo.toRequestBody(): RequestBody {
    return JsonAdapter(MyIceInfoRequest::class.java).toJson(
        MyIceInfoRequest(
            this.bloodType,
            this.birth,
            this.disease,
            this.allergy,
            this.medication,
            this.part1Rel,
            this.part1Phone,
            this.part2Rel,
            this.part2Phone
        )
    ).toRequestBody("application/json".toMediaTypeOrNull())
}

