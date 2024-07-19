package kr.tekit.lion.daongil.data.dto.remote.request

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import kr.tekit.lion.daongil.data.network.LocalDateAdapter
import kr.tekit.lion.daongil.domain.model.UpdateMyPlaceReview
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class MyPlaceReviewRequest(
    val grade: Float,
    val date: LocalDate,
    val content: String,
    val deleteImgUrls: List<String>
)

fun UpdateMyPlaceReview.toRequestBody(): RequestBody {
    val moshi = Moshi.Builder()
        .add(LocalDateAdapter())
        .build()

    return moshi.adapter(MyPlaceReviewRequest::class.java).toJson(
        MyPlaceReviewRequest(
            this.grade,
            this.date,
            this.content,
            this.deleteImgUrls
        )
    ).toRequestBody("application/json".toMediaTypeOrNull())
}