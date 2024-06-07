package kr.tekit.lion.daongil.data.dto.remote.response.emergency.realtime

import com.squareup.moshi.JsonQualifier

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class EmergencyRealtime
