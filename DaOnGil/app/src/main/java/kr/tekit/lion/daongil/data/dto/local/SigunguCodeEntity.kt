package kr.tekit.lion.daongil.data.dto.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.tekit.lion.daongil.domain.model.SigunguCode

@Entity(tableName = "village_code_table")
data class SigunguCodeEntity (
    @PrimaryKey
    val sigunguName: String,
    val sigunguCode: String,
    val areaCode: String
)

fun SigunguCodeEntity.toDomainModel() =
    SigunguCode(
        sigunguName = sigunguName,
        sigunguCode = sigunguCode,
        areaCode = areaCode
    )

fun SigunguCode.toEntity(): SigunguCodeEntity =
    SigunguCodeEntity(
        sigunguName = sigunguName,
        sigunguCode = sigunguCode,
        areaCode = areaCode
    )




