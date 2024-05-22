package kr.tekit.lion.daongil.data.dto.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.tekit.lion.daongil.domain.model.RecentSearchKeyword

@Entity(tableName = "search_keyword_table")
data class RecentSearchKeywordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val keyword: String
)

fun RecentSearchKeywordEntity.toDomainModel(): RecentSearchKeyword {
    return RecentSearchKeyword(keyword)
}

fun RecentSearchKeyword.toEntity(): RecentSearchKeywordEntity {
    return RecentSearchKeywordEntity(
        keyword = keyword
    )
}