package kr.tekit.lion.daongil.presentation.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingViewpagerAdapter(
    private val fragmentList: List<Fragment>,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {
    override fun getItemCount() : Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}