package kr.tekit.lion.daongil.presentation.ext

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R

fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(this, message, duration)
        .setBackgroundTint(ContextCompat.getColor(this.context, R.color.text_secondary))
        .show()
}
