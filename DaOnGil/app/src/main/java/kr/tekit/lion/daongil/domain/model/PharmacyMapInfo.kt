package kr.tekit.lion.daongil.domain.model

import com.squareup.moshi.Moshi

data class PharmacyMapInfo (
    val pharmacyAddress: String?,
    val pharmacyName: String?,
    val pharmacyTel: String?,
    val pharmacyLocation: String?,
    val monTime: String?,
    val tueTime: String?,
    val wedTime: String?,
    val thuTime: String?,
    val friTime: String?,
    val satTime: String?,
    val sunTime: String?,
    val holTime: String?,
    val pharmacyLat: Double?,
    val pharmacyLon: Double?
)

