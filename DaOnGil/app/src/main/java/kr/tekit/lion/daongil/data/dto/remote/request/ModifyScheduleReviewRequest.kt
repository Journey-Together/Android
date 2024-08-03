package kr.tekit.lion.daongil.data.dto.remote.request

import android.graphics.BitmapFactory
import kr.tekit.lion.daongil.data.dto.remote.base.AdapterProvider
import kr.tekit.lion.daongil.domain.model.ModifiedScheduleReview
import kr.tekit.lion.daongil.domain.model.ReviewImage
import kr.tekit.lion.daongil.presentation.ext.compressBitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.net.URL

data class ModifyScheduleReviewRequest(
    val grade: Float?,
    val content: String?,
    //val isPublic: Boolean
)

fun ModifiedScheduleReview.toRequestBody(): RequestBody {
    return AdapterProvider.JsonAdapter(ModifyScheduleReviewRequest::class.java).toJson(
        ModifyScheduleReviewRequest(
            grade = this.grade,
            content = this.content,
            //isPublic = this.isPublic
        )
    ).toRequestBody("application/json".toMediaTypeOrNull())
}

fun List<ReviewImage>.toMultiPartBodyList(): List<MultipartBody.Part> {
    return this.map { image ->
        val fileName =
            image.imagePath?.let { File(it).name } ?: System.currentTimeMillis().toString()

        val bitmap = if (image.imageUrl != null) {
            URL(image.imageUrl).readBytes()
        } else {
            val file = File(image.imagePath!!)
            BitmapFactory.decodeFile(file.path).compressBitmap(60)
        }
        val requestFile = bitmap.toRequestBody("image/jpeg".toMediaTypeOrNull())
        MultipartBody.Part.createFormData("images", fileName, requestFile)
    }
}