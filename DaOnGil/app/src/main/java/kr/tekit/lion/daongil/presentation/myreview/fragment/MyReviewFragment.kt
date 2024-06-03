package kr.tekit.lion.daongil.presentation.myreview.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentMyReviewBinding
import kr.tekit.lion.daongil.domain.model.MyReview
import kr.tekit.lion.daongil.presentation.myreview.adapter.MyReviewRVAdapter

class MyReviewFragment : Fragment(R.layout.fragment_my_review) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMyReviewBinding.bind(view)

        initView(binding)
        settingMyReviewRVAdapter(binding)
    }

    private fun initView(binding: FragmentMyReviewBinding) {
        binding.toolbarMyReview.setNavigationIcon(R.drawable.back_icon)
        binding.toolbarMyReview.setNavigationOnClickListener {
            requireActivity().finish()
        }
    }

    private fun settingMyReviewRVAdapter(binding: FragmentMyReviewBinding) {
        val myReviewList = listOf(
            MyReview("강원특별자치도 동해시", "망상해변", null, "3.5"),
            MyReview("대구 달성군", "비슬산 군립공원", null, "3"),
            MyReview("전라남도 함평군", "함평엑스포공원", null, "4")
        )

        val myReviewRVAdapter = MyReviewRVAdapter(
            myReviewList,
            itemClickListener = { poisition ->
                findNavController().navigate(R.id.action_myReviewFragment_to_myReviewDetailFragment, null)
        })
        binding.recyclerViewMyReview.adapter = myReviewRVAdapter
        binding.recyclerViewMyReview.layoutManager = LinearLayoutManager(context)
    }
}