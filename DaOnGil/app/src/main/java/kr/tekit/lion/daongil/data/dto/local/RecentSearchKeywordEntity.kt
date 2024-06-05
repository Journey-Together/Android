package kr.tekit.lion.daongil.data.dto.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.tekit.lion.daongil.domain.model.RecentSearchKeyword

@Entity(tableName = "search_keyword_table")
data class RecentSearchKeywordEntity(
    val keyword: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

fun RecentSearchKeywordEntity.toDomainModel(): RecentSearchKeyword {
    return RecentSearchKeyword(keyword, id)
}