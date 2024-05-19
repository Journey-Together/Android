package kr.tekit.lion.daongil.repository.areabased

import kr.tekit.lion.daongil.model.KeywordSearch

interface AreaBasedRepository {
    suspend fun getAreaBasedSearch(contentTypeId: String, villageCode: String): List<KeywordSearch>
}