package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.domain.model.KeywordSearch

interface AreaBasedRepository {
    suspend fun getAreaBasedSearch(contentTypeId: String, villageCode: String): List<KeywordSearch>
}