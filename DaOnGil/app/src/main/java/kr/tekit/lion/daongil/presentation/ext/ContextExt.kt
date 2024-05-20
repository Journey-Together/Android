package kr.tekit.lion.daongil.presentation.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

fun Context.showSoftInput(view: View){
    val inputMethodManger = this.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManger.showSoftInput(view, 0)
}
