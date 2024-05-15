package kr.tekit.lion.daongil.dto.response.areacode

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.model.AreaCode

@JsonClass(generateAdapter = true)
data class AreaCodeResponse(
    val response: Response
){
    fun toDomainModel(): List<AreaCode> {
        return response.body.items.item.map {
            AreaCode(it.code, it.name)
        }
    }
}
