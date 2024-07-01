package kr.tekit.lion.daongil.presentation.myreview.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentMyReviewBinding
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
        viewModel.myPlaceReviewList.observe(viewLifecycleOwner) { myPlaceReviewList ->
            if(myPlaceReviewList.isNotEmpty()) {
                binding.notExistReviewLayout.visibility = View.INVISIBLE
                binding.recyclerViewMyReview.visibility = View.VISIBLE

                val myReviewRVAdapter = MyReviewRVAdapter(myPlaceReviewList)
                binding.recyclerViewMyReview.adapter = myReviewRVAdapter
                binding.recyclerViewMyReview.layoutManager = LinearLayoutManager(context)
            } else {
                binding.recyclerViewMyReview.visibility = View.INVISIBLE
                binding.notExistReviewLayout.visibility = View.VISIBLE
                binding.textViewNotExistReview.text = getString(R.string.text_my_review)
            }
        }
    }
}