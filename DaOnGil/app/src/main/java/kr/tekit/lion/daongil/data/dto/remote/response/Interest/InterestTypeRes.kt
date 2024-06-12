package kr.tekit.lion.daongil.data.dto.remote.response.Interest

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.InterestType

@JsonClass(generateAdapter = true)
data class InterestTypeRes(
    val isPhysical: Boolean,
    val isHear: Boolean,
    val isVisual: Boolean,
    val isElderly: Boolean,
    val isChild: Boolean
) {
    // 네트워크 응답 객체인 InterestTypeRes를 도메인 모델 객체인
    // InterestType으로 변환하는 역할.
    fun toDomainModel(): InterestType {
        return InterestType(
            isPhysical = this.isPhysical,
            isHear = this.isHear,
            isVisual = this.isVisual,
            isElderly = this.isElderly,
            isChild = this.isChild
        )
    }
}



