package kr.tekit.lion.daongil.data.dto.remote.response.naverMap

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.ReverseGecodes

@JsonClass(generateAdapter = true)
data class ReverseGecodeResponse(
    val results: List<Result?>?,
    val status: Status?
){
    fun toDomainModel(): ReverseGecodes {
        val domainResults = results?.mapNotNull { it?.toDomainModel() } ?: listOf()
        return ReverseGecodes(
            code = status?.code,
            results = domainResults
        )
    }
}