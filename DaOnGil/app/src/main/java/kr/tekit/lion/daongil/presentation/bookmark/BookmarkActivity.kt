package kr.tekit.lion.daongil.presentation.bookmark

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityBookmarkBinding
import kr.tekit.lion.daongil.domain.model.PlaceBookmark
import kr.tekit.lion.daongil.domain.model.ScheduleBookmark
import kr.tekit.lion.daongil.presentation.bookmark.adapter.PlaceBookmarkRVAdapter
import kr.tekit.lion.daongil.presentation.bookmark.adapter.ScheduleBookmarkRVAdapter
import kr.tekit.lion.daongil.presentation.bookmark.decoration.GridSpacingItemDecoration

class BookmarkActivity : AppCompatActivity() {

    private val binding: ActivityBookmarkBinding by lazy {
        ActivityBookmarkBinding.inflate(layoutInflater)
    }

    private val spanCount = 2
    private val spacing = 16
    private val includeEdge = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        settingToolbar()
        settingTabLayout()
        settingPlaceBookmarkRVAdapter()
    }

    fun settingToolbar() {
        binding.toolbarMyBookmark.setNavigationIcon(R.drawable.back_icon)
        binding.toolbarMyBookmark.setNavigationOnClickListener {
            finish()
        }
    }

    fun settingTabLayout() {
        binding.tabLayoutBookmark.addTab(binding.tabLayoutBookmark.newTab().setText("장소"))
        binding.tabLayoutBookmark.addTab(binding.tabLayoutBookmark.newTab().setText("일정"))

        binding.tabLayoutBookmark.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> settingPlaceBookmarkRVAdapter()
                    1 -> settingScheduleBookmarkRVAdapter()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        });

        binding.tabLayoutBookmark.getTabAt(0)?.select()
    }

    private fun settingPlaceBookmarkRVAdapter() {
        val placeBookmarkList = listOf(
            PlaceBookmark("강원특별자치도 동해시", "망상해변", null, listOf("physical_disability", "visual_impairment", "infant_family", "infant_family")),
            PlaceBookmark("대구 달성군", "비슬산 군립공원", null, listOf("physical_disability", "visual_impairment",)),
            PlaceBookmark("전라남도 함평군", "함평엑스포공원", null, listOf("physical_disability"))
        )

        val placeBookmarkRVAdapter = PlaceBookmarkRVAdapter(
            placeBookmarkList,
            itemClickListener = { }
        )
        binding.recyclerViewBookmark.adapter = placeBookmarkRVAdapter
        binding.recyclerViewBookmark.layoutManager =  GridLayoutManager(this, spanCount)

        if (binding.recyclerViewBookmark.itemDecorationCount == 0) {
            binding.recyclerViewBookmark.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        }
    }

    private fun settingScheduleBookmarkRVAdapter() {
        val scheduleBookmarkList = listOf(
            ScheduleBookmark("서울 탐방 일정",  null, "다온길"),
            ScheduleBookmark("대구는 내가 접수한다", null, "당근"),
            ScheduleBookmark("부산 일정", null, "김사자")
        )

        val scheduleBookmarkRVAdapter = ScheduleBookmarkRVAdapter(
            scheduleBookmarkList,
            itemClickListener = { }
        )

        binding.recyclerViewBookmark.adapter = scheduleBookmarkRVAdapter
        binding.recyclerViewBookmark.layoutManager = GridLayoutManager(this, spanCount)
        if (binding.recyclerViewBookmark.itemDecorationCount == 0) {
            binding.recyclerViewBookmark.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        }
    }
}