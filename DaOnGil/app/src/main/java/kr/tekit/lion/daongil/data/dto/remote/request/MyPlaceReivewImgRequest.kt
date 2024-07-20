package kr.tekit.lion.daongil.data.dto.remote.request

import android.graphics.BitmapFactory
import kr.tekit.lion.daongil.domain.model.MyPlaceReviewImages
import kr.tekit.lion.daongil.presentation.ext.compressBitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun MyPlaceReviewImages.toMultiPartBody(): List<MultipartBody.Part> {
    return this.addImages?.map { imageUrl ->
        val file = File(imageUrl)
        val bitmap = BitmapFactory.decodeFile(file.path).compressBitmap(60)
        val requestFile = bitmap.toRequestBody("MultiPartFile".toMediaTypeOrNull())
        MultipartBody.Part.createFormData("addImages", file.name, requestFile)
    } ?: emptyList()
}