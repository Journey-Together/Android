package kr.tekit.lion.daongil.presentation.main.model

sealed class DisabilityType(val type: String) {
    data object PhysicalDisability : DisabilityType("1")
    data object VisualImpairment : DisabilityType("2")
    data object HearingImpairment : DisabilityType("3")
    data object InfantFamily : DisabilityType("4")
    data object ElderlyPeople : DisabilityType("5")
}