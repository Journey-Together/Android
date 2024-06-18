package kr.tekit.lion.daongil.data.dto.remote.response.member

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.ConcernType

@JsonClass(generateAdapter = true)
data class ConcernTypeResponse(
    val code: Int,
    val data: ConcernTypeData,
    val message: String
) {
    fun toDomainModel(): ConcernType {
        return ConcernType(
            isPhysical = data.isPhysical,
            isHear = data.isHear,
            isVisual = data.isVisual,
            isElderly = data.isElderly,
            isChild = data.isChild
        )
    }
}