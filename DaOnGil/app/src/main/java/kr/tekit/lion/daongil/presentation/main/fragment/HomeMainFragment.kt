package kr.tekit.lion.daongil.presentation.main.fragment

import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentHomeMainBinding
import kr.tekit.lion.daongil.domain.model.Tourist
import kr.tekit.lion.daongil.presentation.main.adapter.HomeLocationRVAdapter
import kr.tekit.lion.daongil.presentation.main.adapter.HomeRecommendRVAdapter
import kr.tekit.lion.daongil.presentation.main.adapter.HomeVPAdapter
import kr.tekit.lion.daongil.presentation.main.dialog.ModeSettingDialog
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

class HomeMainFragment : Fragment(R.layout.fragment_home_main), ModeSettingDialog.ModeSettingDialogInterface {
    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeMainBinding.bind(view)

        settingVPAdapter(binding)
        settingRecommendRVAdapter(binding)
        settingLocationRVAdapter(binding)
        settingDialog()
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
        // 일정 간격으로 슬라이드 변경 (3초마다)
        timer.scheduleAtFixedRate(5000, 5000) {
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

        val homeRecommendRVAdapter = HomeRecommendRVAdapter(touristList, requireContext())
        binding.homeRecommendRv.adapter = homeRecommendRVAdapter
        binding.homeRecommendRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.homeRecommendRv.addItemDecoration(firstItemMarginDecoration(dpToPx(24)))
    }

    // ItemDecoration을 반환하는 함수 정의
    private fun firstItemMarginDecoration(margin: Int): RecyclerView.ItemDecoration {
        return object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildAdapterPosition(view)
                if (position == 0) {
                    outRect.left = margin
                } else {
                    outRect.left = 0
                }
            }
        }
    }

    // dp 값을 픽셀 값으로 변환
    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).toInt()
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
        val dialog = ModeSettingDialog(this)
        dialog.isCancelable = false
        dialog.show(activity?.supportFragmentManager!!, "ModeSettingDialog")
    }

    override fun onSettingBtn() {
        // 고대비모드로 전환
    }
}