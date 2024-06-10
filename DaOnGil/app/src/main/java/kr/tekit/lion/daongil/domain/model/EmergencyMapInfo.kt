package kr.tekit.lion.daongil.domain.model

import java.io.Serializable


data class EmergencyMapInfo(
    val hospitalList: HospitalMapInfo?,
    val emergencyType: String,
    val emergencyId: String?,
    val aedList: AedMapInfo?
): Serializable
