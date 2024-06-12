package kr.tekit.lion.daongil.presentation.myreview.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentMyReviewBinding
import kr.tekit.lion.daongil.domain.model.MyReview
import kr.tekit.lion.daongil.presentation.myreview.adapter.MyReviewRVAdapter

class MyReviewFragment : Fragment(R.layout.fragment_my_review) {

    val args: MyReviewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMyReviewBinding.bind(view)

        initView(binding)
        settingMyReviewRVAdapter(binding)

        val reviewDeleted = args.reviewDeleted
        if (reviewDeleted) {
            Snackbar.make(requireView(), "나의 여행지 후기가 삭제되었습니다.", Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.text_secondary))
                .show()
        }
    }

    private fun initView(binding: FragmentMyReviewBinding) {
        binding.toolbarMyReview.setNavigationIcon(R.drawable.back_icon)
        binding.toolbarMyReview.setNavigationOnClickListener {
            requireActivity().finish()
        }
    }

    private fun settingMyReviewRVAdapter(binding: FragmentMyReviewBinding) {
        val myReviewList = listOf(
            MyReview("강원특별자치도 동해시", "망상해변", "https://access.visitkorea.or.kr/bfvk_img/call?cmd=VIEW&id=8ba0da28-67d3-4385-a5d4-7d8d3cee7b35&mode=row", 3.5),
            MyReview("대구 달성군", "비슬산 군립공원", "https://access.visitkorea.or.kr/bfvk_img/call?cmd=VIEW&id=39749dc0-911a-4609-a236-cc3c31e3e924&mode=row", 3.0),
            MyReview("전라남도 함평군", "함평엑스포공원", "https://access.visitkorea.or.kr/bfvk_img/call?cmd=VIEW&id=a30b8d81-918d-44c6-bb72-ac9c094f74c5&mode=row", 4.0)
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