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

data class ModifyScheduleReviewRequest(
    val grade: Float?,
    val content: String?,
    val deleteImgUrls: List<String>?
)

fun ModifiedScheduleReview.toRequestBody(): RequestBody {
    return AdapterProvider.JsonAdapter(ModifyScheduleReviewRequest::class.java).toJson(
        ModifyScheduleReviewRequest(
            grade = this.grade,
            content = this.content,
            deleteImgUrls = this.deleteImgUrls
        )
    ).toRequestBody("application/json".toMediaTypeOrNull())
}

fun List<ReviewImage>.toMultiPartBodyList(): List<MultipartBody.Part> {
    return this.map { image ->
        val file = image.imagePath?.let { File(it) }
        val bitmap = BitmapFactory.decodeFile(file?.path).compressBitmap(60)
        val requestFile = bitmap.toRequestBody("image/jpeg".toMediaTypeOrNull())
        MultipartBody.Part.createFormData("images", file?.name, requestFile)
    }
}