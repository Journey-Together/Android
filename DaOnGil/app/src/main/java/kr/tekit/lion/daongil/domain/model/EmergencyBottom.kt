package kr.tekit.lion.daongil.domain.model

data class EmergencyBottom(
    val emergencyImage: String?,
    val emergencyName: String,
    val emergencyDistance: String?,
    val emergencyAddress: String,
    val emergencyCall: String,
    val emergencyBed: String?,
    val emergencyType: String,
    val emergencyId: String,
)
