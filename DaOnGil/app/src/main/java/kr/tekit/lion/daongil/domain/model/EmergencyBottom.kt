package kr.tekit.lion.daongil.domain.model

import java.io.Serializable


data class EmergencyBottom(
    val emergencyList: EmergencyMapInfo?,
    val emergencyType: String,
    val emergencyId: String?,
    val aedList: AedMapInfo?
): Serializable
