package kr.tekit.lion.daongil.presentation.main.customview

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.presentation.ext.dpToPx

class CustomPageIndicator(
    private val context: Context,
    indicatorLayout: LinearLayout,
    itemCount: Int
) {
    private val indicators = ArrayList<ImageView>()

    init {
        for (i in 0 until itemCount) {
            val dot = ImageView(context)
            val params = LinearLayout.LayoutParams(
                6.dpToPx(context), // 적절한 크기로 설정
                6.dpToPx(context)  // 적절한 크기로 설정
            )
            params.setMargins(6.dpToPx(context), 0, 6.dpToPx(context), 0) // 적절한 마진으로 설정
            dot.layoutParams = params
            dot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.indicator_unselected))
            indicators.add(dot)
            indicatorLayout.addView(dot)
        }
    }

    fun onPageSelected(position: Int) {
        for (i in indicators.indices) {
            indicators[i].setImageDrawable(
                ContextCompat.getDrawable(context, if (i == position) R.drawable.indicator_selected else R.drawable.indicator_unselected)
            )
        }
    }
}