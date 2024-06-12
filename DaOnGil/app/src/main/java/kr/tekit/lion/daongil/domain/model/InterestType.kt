package kr.tekit.lion.daongil.domain.model

data class InterestType(
    val isPhysical: Boolean = false,
    val isHear: Boolean = false,
    val isVisual: Boolean = false,
    val isElderly: Boolean = false,
    val isChild: Boolean = false,
)
