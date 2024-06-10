package kr.tekit.lion.daongil.data.dto.remote.response.member

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.MyDefaultInfo

@JsonClass(generateAdapter = true)
data class MyDefaultInfoResponse(
    val code: Int,
    val data: MyDefaultInfoData,
    val message: String
){
    fun toDomainModel(): MyDefaultInfo{
        return MyDefaultInfo(
            date = data.date,
            name = data.name,
            profileImg = data.profileImg,
            reviewNum = data.reviewNum
        )
    }
}