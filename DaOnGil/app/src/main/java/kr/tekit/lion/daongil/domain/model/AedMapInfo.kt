package kr.tekit.lion.daongil.domain.model

import java.io.Serializable

data class AedMapInfo (
    val aedName: String?,
    val aedAdress: String?,
    val managerTel: String?,
    val aedTel: String?,
    val aedLocation: String?,
    val monTime: String?,
    val tueTime: String?,
    val wedTime: String?,
    val thuTime: String?,
    val friTime: String?,
    val satTime: String?,
    val sunTime: String?,
    val holTime: String?,
    val sunAvailable: String?,
    val aedLon: Double?,
    val aedLat: Double?
): Serializable