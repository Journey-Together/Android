package kr.tekit.lion.daongil.data.dto.remote.request

import android.graphics.BitmapFactory
import com.squareup.moshi.Json
import kr.tekit.lion.daongil.data.dto.remote.base.AdapterProvider.Companion.JsonAdapter
import kr.tekit.lion.daongil.domain.model.ReviewImg
import kr.tekit.lion.daongil.domain.model.NewScheduleReview
import kr.tekit.lion.daongil.presentation.ext.compressBitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

data class ScheduleReviewRequest(
    val grade: Float,
    val content: String,
    val isPublic: Boolean
)
fun NewScheduleReview.toRequestBody() : RequestBody {
    return JsonAdapter(ScheduleReviewRequest::class.java).toJson(
        ScheduleReviewRequest(
            grade = this.grade,
            content = this.content,
            isPublic = this.isPublic
        )
    ).toRequestBody("application/json".toMediaTypeOrNull())
}

fun List<ReviewImg>.toMultiPartBodyList(): List<MultipartBody.Part> {
    return this.map { image ->
        val file = File(image.path)
        val bitmap = BitmapFactory.decodeFile(file.path).compressBitmap(60)
        val requestFile = bitmap.toRequestBody("image/jpeg".toMediaTypeOrNull())
        MultipartBody.Part.createFormData("images", file.name, requestFile)
    }
}