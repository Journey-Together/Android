package kr.tekit.lion.daongil.data.dto.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.tekit.lion.daongil.domain.model.VillageCode

@Entity(tableName = "village_code_table")
data class VillageCodeEntity (
    @PrimaryKey
    val villageName: String,
    val villageCode: String,
    val areaCode: String
)

fun VillageCodeEntity.toDomainModel() =
    VillageCode(
        villageName = villageName,
        villageCode = villageCode,
        areaCode = areaCode
    )

fun VillageCode.toEntity(): VillageCodeEntity =
    VillageCodeEntity(
        villageName = villageName,
        villageCode = villageCode,
        areaCode = areaCode
    )




