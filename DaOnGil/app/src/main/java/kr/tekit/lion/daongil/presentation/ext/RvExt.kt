package kr.tekit.lion.daongil.presentation.ext

import android.util.Log
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addOnScrollEndListener(
    threshold: Int = 3,
    callback: () -> Unit,
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == SCROLL_STATE_IDLE && recyclerView.hasLessItemThan(threshold)) {
                Log.d("MyOkHttpResult", " invoke")

                callback.invoke()
            }
        }

//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            if (recyclerView.hasLessItemThan(threshold)) {
//                callback.invoke()
//            }
//        }

        private fun RecyclerView.hasLessItemThan(threshold: Int): Boolean {
            if (isLayoutRequested) {
                return false
            }
            (layoutManager as? LinearLayoutManager)?.let {
                val lastVisibleItem = it.findLastVisibleItemPosition()
                Log.d("MyOkHttpResult", " lastVisibleItem $lastVisibleItem")
                Log.d("MyOkHttpResult", " it.itemCount ${it.itemCount}")
                Log.d("MyOkHttpResult", " $lastVisibleItem >= ${it.itemCount} - $threshold")

                return lastVisibleItem >= it.itemCount - threshold
            }
            return false
        }
    })
}