package kr.tekit.lion.daongil.data.dto.remote.response.naverMap

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.ReverseGecode

@JsonClass(generateAdapter = true)
data class Result(
    val code: Code?,
    val name: String?,
    val region: Region?
){
    fun toDomainModel(): ReverseGecode {
        return ReverseGecode(
            area = region?.area1?.name,
            areaDetail = region?.area2?.name
        )
    }
}