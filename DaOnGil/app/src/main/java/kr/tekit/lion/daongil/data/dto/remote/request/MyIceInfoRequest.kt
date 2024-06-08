package kr.tekit.lion.daongil.data.dto.remote.request

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.MyIceInfo

@JsonClass(generateAdapter = true)
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

fun MyIceInfo.toRequestModel() = MyIceInfoRequest(
    bloodType = bloodType,
    birth = birth,
    disease = disease,
    allergy = disease,
    medication = medication,
    part1_rel = part1Rel,
    part1_phone = part1Phone,
    part2_rel = part2Rel,
    part2_phone = part2Phone
)