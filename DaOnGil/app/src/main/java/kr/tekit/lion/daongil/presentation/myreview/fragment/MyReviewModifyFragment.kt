package kr.tekit.lion.daongil.presentation.myreview.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ext.SdkExtensions
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentMyReviewModifyBinding
import kr.tekit.lion.daongil.presentation.main.customview.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.customview.ConfirmDialogInterface
import kr.tekit.lion.daongil.presentation.myreview.adapter.MyReviewModifyImageRVAdapter

class MyReviewModifyFragment : Fragment(R.layout.fragment_my_review_modify), ConfirmDialogInterface {

    private val imagesList: ArrayList<Uri> = ArrayList()

    val imageRVAdapter: MyReviewModifyImageRVAdapter by lazy {
        MyReviewModifyImageRVAdapter(imagesList)
    }

    @SuppressLint("NotifyDataSetChanged")
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                imagesList.add(uri)
                imageRVAdapter.notifyDataSetChanged()
            }
        }

    @SuppressLint("NotifyDataSetChanged")
    private val albumLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // 사진 선택을 완료한 후 돌아왔다면
            if (result.resultCode == Activity.RESULT_OK) {
                // 선택한 이미지의 Uri 가져오기
                val uri = result.data?.data
                uri?.let {
                    // 이미지를 리스트에 추가하고 어댑터에 데이터 변경을 알림
                    imagesList.add(it)
                    imageRVAdapter.notifyDataSetChanged()
                }
            }
        }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startAlbumLauncher()
            } else {
                val permissionDialog = ConfirmDialog(
                    this, "권한 설정", "갤러리 이용을 위해 권한 설정이 필요합니다", "권한 설정",
                    R.color.button_tertiary, R.color.white
                )
                permissionDialog.isCancelable = false
                permissionDialog.show(requireActivity().supportFragmentManager, "PermissionDialog")
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMyReviewModifyBinding.bind(view)

        settingToolbar(binding)
        settingImageRVAdapter(binding)
        settingButton(binding)
        setDummyData(binding)
    }

    private fun setDummyData(binding: FragmentMyReviewModifyBinding) {
        // 더미 데이터
        val dummyImage = "https://access.visitkorea.or.kr/bfvk_img/call?cmd=VIEW&id=8ba0da28-67d3-4385-a5d4-7d8d3cee7b35&mode=row"
        val dummyTitle = "망상해변"
        val dummyAddress = "강원특별자치도 동해시 동해대로 6270-10 (망상동)"
        val dummyDate = "2024.05.24 금"
        val dummySatisfaction = 3.5
        val dummyReview = "역시 바다입니다.\n" +
                "탁 트여서 풍경도 좋고, 즐기기에 너무 좋습니다!\n" +
                "시설도 잘 되어있어서 만족스러웠습니다.\n" +
                "오랜만에 걱정 없이 즐긴 거 같습니다.\n" +
                "다음에도 또 방문하고 싶어요."
        val dummyImages = listOf(
            Uri.parse("https://access.visitkorea.or.kr/bfvk_img/call?cmd=VIEW&id=72eabcf0-7c1f-416c-8c47-ac007e6e20a9&mode=row"),
            Uri.parse("https://access.visitkorea.or.kr/bfvk_img/call?cmd=VIEW&id=22d309c5-09cf-458a-815a-e45bb1eeced7&mode=row"),
            Uri.parse("https://access.visitkorea.or.kr/bfvk_img/call?cmd=VIEW&id=678ff254-540f-439a-9e28-3864829f3530&mode=row")
        )

        binding.textViewMyReviewModifyTitle.text = dummyTitle
        binding.textViewMyReviewModifyAddress.text = dummyAddress
        binding.buttonModifyReviewDate.text = dummyDate
        binding.ratingbarMyReviewModify.rating = dummySatisfaction.toFloat()
        binding.textFieldMyReviewModifyWrite.setText(dummyReview)
        Glide.with(binding.ImageViewMyReviewModify.context)
            .load(dummyImage)
            .error(R.drawable.empty_view)
            .into(binding.ImageViewMyReviewModify)
        imagesList.clear()
        imagesList.addAll(dummyImages)
        imageRVAdapter.notifyDataSetChanged()
    }

    private fun settingToolbar(binding: FragmentMyReviewModifyBinding) {
        binding.toolbarMyReviewModify.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_myReviewModifyFragment_to_myReviewDetailFragment)
        }
    }

    private fun settingButton(binding: FragmentMyReviewModifyBinding) {
        binding.imageButtonMyReviewModifyAddBtn.setOnClickListener {
            if (isPhotoPickerAvailable()) {
                this.pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                checkPermission()
            }
        }

        binding.buttonMyReviewModify.setOnClickListener {
            findNavController().navigate(R.id.action_myReviewModifyFragment_to_myReviewDetailFragment)
        }
    }

    private fun settingImageRVAdapter(binding: FragmentMyReviewModifyBinding) {
        binding.recyclerViewMyReviewModify.adapter = imageRVAdapter
    }

    private fun isPhotoPickerAvailable(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            true
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            SdkExtensions.getExtensionVersion(Build.VERSION_CODES.R) >= 2
        } else {
            false
        }
    }

    fun checkPermission() {
        val permissionReadExternal = android.Manifest.permission.READ_EXTERNAL_STORAGE

        val permissionReadExternalGranted = ContextCompat.checkSelfPermission(
            requireContext().applicationContext,
            permissionReadExternal
        ) == PackageManager.PERMISSION_GRANTED

        // 포토피커를 사용하지 못하는 버전만 권한 확인 (SDK 30 미만)
        if (permissionReadExternalGranted) {
            startAlbumLauncher()
        } else {
            permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun startAlbumLauncher() {
        val albumIntent = Intent(Intent.ACTION_GET_CONTENT)
        albumIntent.type = "image/*"  // 이미지 타입만 선택하도록 설정
        albumLauncher.launch(albumIntent)
    }

    override fun onPosBtnClick() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}