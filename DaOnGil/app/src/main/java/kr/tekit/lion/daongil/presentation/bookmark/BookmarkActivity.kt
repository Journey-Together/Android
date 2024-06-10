package kr.tekit.lion.daongil.presentation.bookmark

import android.os.Bundle
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
        viewModel.placeBookmarkList.observe(this) { placeBookmarkList ->
            if (placeBookmarkList.isNotEmpty()) {
                binding.recyclerViewBookmark.visibility = View.VISIBLE
                val placeBookmarkRVAdapter = PlaceBookmarkRVAdapter(
                    placeBookmarkList,
                    itemClickListener = { }
                )
                binding.recyclerViewBookmark.adapter = placeBookmarkRVAdapter
            } else {
                binding.notExistBookmarkLayout.visibility = View.VISIBLE
                binding.textViewNotExistBookmark.text = getString(R.string.text_place_bookmark)
            }
        }
    }

    private fun settingScheduleBookmarkRVAdapter() {
        viewModel.planBookmarkList.observe(this) { planBookmarkList ->
            if (planBookmarkList.isNotEmpty()) {
                binding.recyclerViewBookmark.visibility = View.VISIBLE
                val planBookmarkRVAdapter = PlanBookmarkRVAdapter(
                    planBookmarkList,
                    itemClickListener = { }
                )
                binding.recyclerViewBookmark.adapter = planBookmarkRVAdapter
            } else {
                binding.notExistBookmarkLayout.visibility = View.VISIBLE
                binding.textViewNotExistBookmark.text = getString(R.string.text_plan_bookmark)
            }
        }
    }
}