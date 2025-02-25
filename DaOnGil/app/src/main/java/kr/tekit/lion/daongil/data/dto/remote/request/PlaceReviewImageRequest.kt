package kr.tekit.lion.daongil.data.dto.remote.request

import android.graphics.BitmapFactory
import kr.tekit.lion.daongil.domain.model.NewReviewImages
import kr.tekit.lion.daongil.presentation.ext.compressBitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun NewReviewImages.toMultiPartBody(): List<MultipartBody.Part> {
    val parts = mutableListOf<MultipartBody.Part>()

    this.images?.let { images ->
        for (imageUrl in images) {
            val file = File(imageUrl)
            val bitmap = BitmapFactory.decodeFile(file.path).compressBitmap(60)
            val requestFile = bitmap.toRequestBody("MultiPartFile".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("images", file.name, requestFile)
            parts.add(part)
        }
    }

    return parts
}
