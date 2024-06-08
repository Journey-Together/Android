package kr.tekit.lion.daongil.data.ext

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun File.toMultipartBody(): MultipartBody.Part {

    // 파일 객체를 RequestBody Type으로 변환
    val requestFile = this.asRequestBody("image/*".toMediaTypeOrNull())

    // MultipartBody.Part Type으로 변환
    return MultipartBody.Part.createFormData("storeMainImage", this.name, requestFile)
}