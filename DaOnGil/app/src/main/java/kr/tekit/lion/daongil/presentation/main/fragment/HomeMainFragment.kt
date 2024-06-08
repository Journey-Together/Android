package kr.tekit.lion.daongil.presentation.main.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import kr.tekit.lion.daongil.HighThemeApp
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentHomeMainBinding
import kr.tekit.lion.daongil.domain.model.Tourist
import kr.tekit.lion.daongil.presentation.home.DetailActivity
import kr.tekit.lion.daongil.presentation.main.adapter.HomeLocationRVAdapter
import kr.tekit.lion.daongil.presentation.main.adapter.HomeRecommendRVAdapter
import kr.tekit.lion.daongil.presentation.main.adapter.HomeVPAdapter
import kr.tekit.lion.daongil.presentation.main.dialog.ModeSettingDialog
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

class HomeMainFragment : Fragment(R.layout.fragment_home_main), HomeRecommendRVAdapter.OnRecommendClickListener {
    private val app = HighThemeApp.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val theme = HighThemeApp.getInstance().getThemePrefs()

        when (theme) {
            "night" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "basic" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeMainBinding.bind(view)

        if (app.isFirstLogin()) {
            settingDialog()
        }

        settingVPAdapter(binding)
        settingRecommendRVAdapter(binding)
        settingLocationRVAdapter(binding)
        settingHighcontrastBtn(binding)
    }

    private fun settingVPAdapter(binding: FragmentHomeMainBinding) {
        val images = listOfNotNull(
            ContextCompat.getDrawable(requireContext(), R.drawable.item_home_vp1),
            ContextCompat.getDrawable(requireContext(), R.drawable.item_home_vp2),
            ContextCompat.getDrawable(requireContext(), R.drawable.item_home_vp3)
        )

        val homeVPAdapter = HomeVPAdapter(images)
        binding.homeVp.adapter = homeVPAdapter
        binding.homeVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.homeVpIndicator.setViewPager(binding.homeVp)
        startAutoSlide(homeVPAdapter, binding)
    }

    private fun startAutoSlide(adpater : HomeVPAdapter, binding: FragmentHomeMainBinding) {
        val timer = Timer()
        val handler = Handler(Looper.getMainLooper())

        // 일정 간격으로 슬라이드 변경 (4초마다)
        timer.scheduleAtFixedRate(3000, 4000) {
            handler.post {
                val nextItem = binding.homeVp.currentItem + 1
                if (nextItem < adpater.itemCount) {
                    binding.homeVp.currentItem = nextItem
                } else {
                    binding.homeVp.currentItem = 0 // 마지막 페이지에서 첫 페이지로 순환
                }
            }
        }
    }

    private fun settingRecommendRVAdapter(binding: FragmentHomeMainBinding) {
        // 임시 더미데이터
        val touristList = listOf(
            Tourist("서울 종로구", "세종문화회관", null, listOf("physical_disability", "visual_impairment", "infant_family", "infant_family")),
            Tourist("서울 종로구", "세종문화회관", null, listOf("physical_disability", "visual_impairment", "infant_family")),
            Tourist("서울 종로구", "세종문화회관", null, listOf("physical_disability", "visual_impairment", "infant_family")),
            Tourist("서울 종로구", "세종문화회관", null, listOf("physical_disability", "visual_impairment", "infant_family"))
            )

        val homeRecommendRVAdapter = HomeRecommendRVAdapter(touristList, requireContext(), this)
        binding.homeRecommendRv.adapter = homeRecommendRVAdapter
        binding.homeRecommendRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun settingLocationRVAdapter(binding: FragmentHomeMainBinding) {
        // 임시 더미데이터
        val touristList = listOf(
            Tourist("서울 종로구", "세종문화회관", null, listOf("physical_disability", "visual_impairment", "infant_family")),
            Tourist("서울 종로구", "세종문화회관", null, listOf("physical_disability", "visual_impairment", "elderly_people"))
            )

        val homeLocationRVAdapter = HomeLocationRVAdapter(touristList, requireContext())
        binding.homeMyLocationRv.adapter = homeLocationRVAdapter
        binding.homeMyLocationRv.layoutManager = LinearLayoutManager(context)
    }

    private fun settingDialog() {
        val dialog = ModeSettingDialog()
        dialog.isCancelable = false
        dialog.show(activity?.supportFragmentManager!!, "ModeSettingDialog")
    }

    private fun settingHighcontrastBtn(binding: FragmentHomeMainBinding) {
        binding.homeHighcontrastBtn.setOnClickListener {
            if (app.getThemePrefs() == "basic") {
                app.setThemePrefs("night")
                requireActivity().recreate()
            } else {
                app.setThemePrefs("basic")
                requireActivity().recreate()
            }
        }
    }

    override fun onRecommendClicked(tourist: Tourist) {
        val intent = Intent(context, DetailActivity::class.java)
        startActivity(intent)
    }
}