package kr.tekit.lion.daongil.presentation.bookmark

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityBookmarkBinding
import kr.tekit.lion.daongil.presentation.bookmark.adapter.PlaceBookmarkRVAdapter
import kr.tekit.lion.daongil.presentation.bookmark.adapter.PlanBookmarkRVAdapter
import kr.tekit.lion.daongil.presentation.bookmark.vm.BookmarkViewModel
import kr.tekit.lion.daongil.presentation.bookmark.vm.BookmarkViewModelFactory

class BookmarkActivity : AppCompatActivity() {

    private val binding: ActivityBookmarkBinding by lazy {
        ActivityBookmarkBinding.inflate(layoutInflater)
    }

    private val viewModel: BookmarkViewModel by viewModels { BookmarkViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        settingToolbar()
        settingTabLayout()
        settingPlaceBookmarkRVAdapter()
    }

    fun settingToolbar() {
        binding.toolbarMyBookmark.setNavigationOnClickListener {
            finish()
        }
    }

    fun settingTabLayout() {
        binding.tabLayoutBookmark.addTab(binding.tabLayoutBookmark.newTab().setText(getString(R.string.tab_text_place)))
        binding.tabLayoutBookmark.addTab(binding.tabLayoutBookmark.newTab().setText(getString(R.string.tab_text_plan)))

        binding.tabLayoutBookmark.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        Log.d("BookmarkActivity_check", "Place tab selected")
                        settingPlaceBookmarkRVAdapter()
                    }
                    1 -> {
                        Log.d("BookmarkActivity_check", "Plan tab selected")
                        settingScheduleBookmarkRVAdapter()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.tabLayoutBookmark.getTabAt(0)?.select()
    }

    private fun settingPlaceBookmarkRVAdapter() {
        viewModel.placeBookmarkList.observe(this) { placeBookmarkList ->
            Log.d("BookmarkActivity_check", "Place bookmarks updated: $placeBookmarkList")
            if (placeBookmarkList.isNotEmpty()) {
                binding.notExistBookmarkLayout.visibility = View.INVISIBLE
                binding.recyclerViewBookmark.visibility = View.VISIBLE
                val placeBookmarkRVAdapter = PlaceBookmarkRVAdapter(
                    placeBookmarkList,
                    itemClickListener = { },
                    onBookmarkClick = { placeId ->
                        viewModel.updatePlaceBookmark(placeId)
                    }
                )
                binding.recyclerViewBookmark.adapter = placeBookmarkRVAdapter
            } else {
                binding.recyclerViewBookmark.visibility = View.INVISIBLE
                binding.notExistBookmarkLayout.visibility = View.VISIBLE
                binding.textViewNotExistBookmark.text = getString(R.string.text_place_bookmark)
            }
        }
    }

    private fun settingScheduleBookmarkRVAdapter() {
        viewModel.planBookmarkList.observe(this) { planBookmarkList ->
            Log.d("BookmarkActivity_check", "Plan bookmarks updated: $planBookmarkList")
            if (planBookmarkList.isNotEmpty()) {
                binding.notExistBookmarkLayout.visibility = View.INVISIBLE
                binding.recyclerViewBookmark.visibility = View.VISIBLE
                val planBookmarkRVAdapter = PlanBookmarkRVAdapter(
                    planBookmarkList,
                    itemClickListener = { },
                    onBookmarkClick = { planId ->
                        viewModel.updatePlanBookmark(planId)
                    }
                )
                binding.recyclerViewBookmark.adapter = planBookmarkRVAdapter
            } else {
                binding.recyclerViewBookmark.visibility = View.INVISIBLE
                binding.notExistBookmarkLayout.visibility = View.VISIBLE
                binding.textViewNotExistBookmark.text = getString(R.string.text_plan_bookmark)
            }
        }
    }
}