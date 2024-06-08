package kr.tekit.lion.daongil.domain.model

data class EmergencyBottom(
    val emergencyList: EmergencyMapInfo?,
    val emergencyType: String,
    val emergencyId: String?,
    val aedList: AedMapInfo?
    // 약국이랑 aed list도 추가
)
