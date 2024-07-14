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
import androidx.navigation.fragment.navArgs
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentMyReviewModifyBinding
import kr.tekit.lion.daongil.domain.model.MyPlaceReviewInfo
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialogInterface
import kr.tekit.lion.daongil.presentation.myreview.adapter.MyReviewModifyImageRVAdapter

class MyReviewModifyFragment : Fragment(R.layout.fragment_my_review_modify),
    ConfirmDialogInterface {

    private val imagesList: ArrayList<Uri> = ArrayList()

    private val imageRVAdapter: MyReviewModifyImageRVAdapter by lazy {
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

        val args: MyReviewModifyFragmentArgs by navArgs()
        val review = args.myPlaceReviewInfo

        settingToolbar(binding)
        settingReviewData(binding, review)
        settingImageRVAdapter(binding)
        settingButton(binding)
    }

    private fun settingReviewData(binding: FragmentMyReviewModifyBinding, review: MyPlaceReviewInfo) {
        binding.textViewMyReviewModifyTitle.text = review.name
        binding.ratingbarMyReviewModify.rating = review.grade
        binding.textFieldMyReviewModifyDate.setText(review.date.toString())
        binding.textFieldMyReviewModifyWrite.setText(review.content)

        imagesList.clear()
        imagesList.addAll(review.images.map { Uri.parse(it) })
        imageRVAdapter.notifyDataSetChanged()
    }

    private fun settingToolbar(binding: FragmentMyReviewModifyBinding) {
        binding.toolbarMyReviewModify.setNavigationOnClickListener {
        }
    }

    private fun settingButton(binding: FragmentMyReviewModifyBinding) {
        binding.layoutImageAddBtn.setOnClickListener {
            if (isPhotoPickerAvailable()) {
                this.pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                checkPermission()
            }
        }

        binding.buttonMyReviewModify.setOnClickListener {
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

    private fun checkPermission() {
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