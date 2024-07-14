package kr.tekit.lion.daongil.presentation.myreview.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentMyReviewBinding
import kr.tekit.lion.daongil.presentation.ext.addOnScrollEndListener
import kr.tekit.lion.daongil.presentation.ext.showConfirmDialog
import kr.tekit.lion.daongil.presentation.myreview.adapter.MyReviewRVAdapter
import kr.tekit.lion.daongil.presentation.myreview.vm.MyReviewViewModel
import kr.tekit.lion.daongil.presentation.myreview.vm.MyReviewViewModelFactory

class MyReviewFragment : Fragment(R.layout.fragment_my_review) {

    private val viewModel: MyReviewViewModel by activityViewModels { MyReviewViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMyReviewBinding.bind(view)

        settingToolbar(binding)
        settingMyReviewRVAdapter(binding)
    }

    private fun settingToolbar(binding: FragmentMyReviewBinding) {
        binding.toolbarMyReview.setNavigationOnClickListener {
            requireActivity().finish()
        }
    }

    private fun settingMyReviewRVAdapter(binding: FragmentMyReviewBinding) {
        viewModel.myPlaceReview.observe(viewLifecycleOwner) { myPlaceReview ->
            if(myPlaceReview.myPlaceReviewInfoList.isNotEmpty()) {
                binding.notExistReviewLayout.visibility = View.INVISIBLE
                binding.recyclerViewMyReview.visibility = View.VISIBLE

                val myReviewRVAdapter = MyReviewRVAdapter(
                    myPlaceReview,
                    myPlaceReview.myPlaceReviewInfoList,
                    onMoveReviewListClick = {},
                    onModifyClick = { myPlaceReviewInfo ->
                        val action = MyReviewFragmentDirections.actionMyReviewFragmentToMyReviewModifyFragment(myPlaceReviewInfo)
                        findNavController().navigate(action)
                    },
                    onDeleteClick = { reviewId ->
                        requireContext().showConfirmDialog(
                            parentFragmentManager,
                            "여행지 후기 삭제",
                            "삭제한 데이터는 되돌릴 수 없습니다.",
                            "삭제하기"
                        ) {
                            viewModel.deleteMyPlaceReview(reviewId)
                        }
                    }
                )

                val rvState = binding.recyclerViewMyReview.layoutManager?.onSaveInstanceState()
                binding.recyclerViewMyReview.adapter = myReviewRVAdapter
                binding.recyclerViewMyReview.layoutManager = LinearLayoutManager(context)
                rvState?.let {
                    binding.recyclerViewMyReview.layoutManager?.onRestoreInstanceState(it)
                }

                binding.recyclerViewMyReview.addOnScrollEndListener {
                    if (viewModel.isLastPage.value == false) {
                        viewModel.getNextMyPlaceReview(5)
                    }
                }
            } else {
                binding.recyclerViewMyReview.visibility = View.INVISIBLE
                binding.notExistReviewLayout.visibility = View.VISIBLE
                binding.textViewNotExistReview.text = getString(R.string.text_my_review)
            }
        }
    }
}