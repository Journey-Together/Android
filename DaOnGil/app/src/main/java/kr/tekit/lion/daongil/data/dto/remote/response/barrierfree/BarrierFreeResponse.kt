package kr.tekit.lion.daongil.data.dto.remote.response.barrierfree

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.BarrierFree

@JsonClass(generateAdapter = true)
data class BarrierFreeResponse(
    val response: Response
) {
    fun toDomainModel(): List<BarrierFree> {
        return response.body.items.item.map {
            BarrierFree(
                audioGuide = it.audioGuide, // 수화 안내
                auditorium = it.auditorium, // 관람석
                babySpareChair = it.babySpareChair, // 유아용 보조 의자
                bigPrint = it.bigPrint, // 큰활자 홍보물
                blindHandicapEtc = it.blindHandicapEtc, // 시각 장애 기타 상세
                braileBlock = it.braileBlock, // 점자 블록
                brailePromotion = it.brailePromotion, // 점자 홍보물 및 점자 표지판
                contentId = it.contentId, // 콘텐츠 ID
                elevator = it.elevator, // 엘리베이터
                exit = it.exit, // 출입 통로
                guideHuman = it.guideHuman, // 안내 요원
                guideSystem = it.guideSystem, // 유도 안내 설비
                handicapEtc = it.handicapEtc, // 지체 장애 기타 상세
                hearingHandicapEtc = it.hearingHandicapEtc, // 청각 장애 기타 상세
                hearingRoom = it.hearingRoom, // 객실
                helpDog = it.helpDog, //보조견 동반
                infantsFamilyEtc = it.infantsFamilyEtc, // 영유아 가족 기타 상세
                lactationRoom = it.lactationRoom, // 수유실
                parking = it.parking, // 주차 여부
                promotion = it.promotion, // 홍보물
                publicTransport = it.publicTransport, // 접근로
                restroom = it.restroom, // 화장실
                room = it.room, // 객실
                route = it.route, //대중 교통
                signGuide = it.signGuide, //수화 안내
                stroller = it.stroller, // 유모차
                ticketOffice = it.ticketOffice, // 매표소
                videoGuide = it.videoGuide, // 자막 비디오 가이드, 영상 자막 안내
                wheelchair = it.wheelchair // 휠체어
            )
        }
    }
}
