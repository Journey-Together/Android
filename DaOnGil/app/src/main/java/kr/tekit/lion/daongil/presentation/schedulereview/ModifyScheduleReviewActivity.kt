package kr.tekit.lion.daongil.presentation.schedulereview

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ext.SdkExtensions
import android.provider.Settings
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityModifyScheduleReviewBinding
import kr.tekit.lion.daongil.domain.model.ReviewImage
import kr.tekit.lion.daongil.presentation.ext.setImage
import kr.tekit.lion.daongil.presentation.ext.toAbsolutePath
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialogInterface
import kr.tekit.lion.daongil.presentation.schedulereview.adapter.ModifyReviewImageAdapter
import kr.tekit.lion.daongil.presentation.schedulereview.vm.ModifyScheduleReviewViewModel
import kr.tekit.lion.daongil.presentation.schedulereview.vm.ModifyScheduleReviewViewModelFactory

class ModifyScheduleReviewActivity : AppCompatActivity(), ConfirmDialogInterface {

    private val viewModel : ModifyScheduleReviewViewModel by viewModels {
        ModifyScheduleReviewViewModelFactory()
    }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                saveImageDataAndPath(uri)
            }
        }

    private val albumLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // 사진 선택을 완료한 후 돌아왔다면
            if (result.resultCode == Activity.RESULT_OK) {
                // 선택한 이미지의 Uri 가져오기
                val uri = result.data?.data
                uri?.let {
                    saveImageDataAndPath(uri)
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
                permissionDialog.show(supportFragmentManager, "ScheduleReviewbnPermissionDialog")
            }
        }

    private val binding: ActivityModifyScheduleReviewBinding by lazy {
        ActivityModifyScheduleReviewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val planId = intent.getLongExtra("planId", -1)

        initToolbar()
        loadScheduleReview(planId)

        initReviewContentWatcher()
        settingImageRVAdapter()
        settingButtonClickListner()
    }

    private fun initToolbar() {
        binding.toolbarModifyScheReview.setNavigationOnClickListener {
            finish()
        }
    }

    private fun loadScheduleReview(planId: Long){
        viewModel.getScheduleReviewInfo(planId)

        initView()
    }

    private fun initView() {
        viewModel.originalReview.observe(this@ModifyScheduleReviewActivity) { scheduleReviewInfo ->
            with(binding){
                textViewModifyScheReviewName.text = scheduleReviewInfo.title
                textViewModifyScheReviewPeriod.text = getString(
                    R.string.text_schedule_period,
                    scheduleReviewInfo?.startDate,
                    scheduleReviewInfo?.endDate
                )
                val isImageAvailable = scheduleReviewInfo.imageUrl.isNotEmpty()
                if(isImageAvailable){
                    this@ModifyScheduleReviewActivity.setImage(
                        imageViewModifyScheReviewThumb,
                        scheduleReviewInfo.imageUrl
                    )
                }
                val isPublic = scheduleReviewInfo.isPublic
                if(isPublic) radioButtonModifyScheReviewPublic.isChecked = true
                else radioButtonModifyScheReviewPrivate.isChecked = true

                ratingbarModifyScheReview.rating = scheduleReviewInfo.grade
                editTextModifyScheReviewContent.setText(scheduleReviewInfo.content)
            }
        }

        viewModel.numOfImages.observe(this@ModifyScheduleReviewActivity) { numOfImages ->
            binding.textViewModifyScheReviewPhotoNum.text =
                getString(R.string.text_num_of_images, numOfImages)
        }
    }

    private fun settingImageRVAdapter() {
        viewModel.imageList.observe(this) { imageList ->
            val modifyReviewImageAdapter = ModifyReviewImageAdapter(imageList) { position ->
                viewModel.removeReviewImageFromList(position)
            }
            binding.recyclerViewModifyScheReviewPhotos.adapter = modifyReviewImageAdapter
        }
    }

    private fun settingButtonClickListner() {
        binding.apply {
            imageButtonModifyScheReviewPhotoAdd.setOnClickListener {
                if (!viewModel.isMoreImageAttachable()) {
                    showSnackBar(it, "사진은 최대 4개까지 첨부할 수 있습니다")
                    return@setOnClickListener
                }

                if (isPhotoPickerAvailable()) {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                } else {
                    checkPermission()
                }
            }

            buttonModifyScheReivewSubmit.setOnClickListener {
                val isValid = isReviewValid()
                if(!isValid) return@setOnClickListener

                // TO DO - API 연결
            }
        }
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

    // 갤러리 접근 권한 확인 함수
    private fun checkPermission() {
        val permissionReadExternal = android.Manifest.permission.READ_EXTERNAL_STORAGE

        val permissionReadExternalGranted = ContextCompat.checkSelfPermission(
            this,
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
        // 앱 설정 화면으로 이동
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun saveImageDataAndPath(uri: Uri) {
        val imagePath = toAbsolutePath(uri)
        if (imagePath != null) {
            val newImage = ReviewImage(imageUri = uri, imagePath = imagePath)
            viewModel.addNewReviewImage(newImage)
        }
    }

    private fun isReviewValid(): Boolean {
        with(binding){
            val isPrivateOrPublic = buttonGroupModifyScheReviewPublic.checkedRadioButtonId
            if(isPrivateOrPublic == View.NO_ID){
                showSnackBar(buttonGroupModifyScheReviewPublic, "여행 일정 공개 범주를 선택해주세요")
                return false
            }

            val reviewContent = editTextModifyScheReviewContent.text.toString()
            if(reviewContent.isBlank()){
                inputLayoutModifyScheReviewContent.error = getString(R.string.text_warning_review_content_empty)
                return false
            }
        }
        return true
    }

    private fun initReviewContentWatcher() {
        with(binding){
            editTextModifyScheReviewContent.addTextChangedListener {
                inputLayoutModifyScheReviewContent.error = null
            }
        }
    }

    private fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(getColor(R.color.text_secondary))
            .show()
    }

}