package kr.tekit.lion.daongil.data.dto.local

import kr.tekit.lion.daongil.domain.model.User

data class UserResponse (
    val memberId: Int,
    val email: String = "",
    val name: String = "",
    val profileUuid: String = "",
){
    fun toDomainModel(): User {
        return User(
            memberId = memberId,
            email = email,
            name = name,
            profileUuid = profileUuid
        )
    }
}