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
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityWriteScheduleReviewBinding
import kr.tekit.lion.daongil.domain.model.NewScheduleReview
import kr.tekit.lion.daongil.presentation.ext.toAbsolutePath
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialogInterface
import kr.tekit.lion.daongil.presentation.schedule.ResultCode
import kr.tekit.lion.daongil.presentation.schedule.ResultCode.RESULT_REVIEW_WRITE
import kr.tekit.lion.daongil.presentation.schedulereview.adapter.WriteReviewImageAdapter
import kr.tekit.lion.daongil.presentation.schedulereview.vm.WriteScheduleReviewViewModel
import kr.tekit.lion.daongil.presentation.schedulereview.vm.WriteScheduleReviewViewModelFactory

class WriteScheduleReviewActivity : AppCompatActivity() ,ConfirmDialogInterface {

    private val viewModel : WriteScheduleReviewViewModel by viewModels { WriteScheduleReviewViewModelFactory() }

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

    private val binding : ActivityWriteScheduleReviewBinding by lazy {
        ActivityWriteScheduleReviewBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val planId = intent.getLongExtra("planId", -1)

        initToolbar()
        initView(planId)
        initReviewContentWatcher()
        settingImageRVAdapter()
        settingButtonClickListner(planId)
    }

    private fun initToolbar(){
        binding.toolbarWriteScheReview.setNavigationOnClickListener {
                finish()
        }
    }
    private fun initView(planId: Long) {
        viewModel.getBriefScheduleInfo(planId)
        viewModel.briefSchedule.observe(this@WriteScheduleReviewActivity) { briefSchedule ->
            binding.apply {
                textViewWriteScheReviewName.text = briefSchedule?.title
                textViewWriteScheReviewPeriod.text = getString(
                    R.string.text_schedule_period,
                    briefSchedule?.startDate,
                    briefSchedule?.endDate
                )
                Glide.with(imageViewWriteScheReviewThumb.context)
                    .load(briefSchedule?.imageUrl)
                    .error(R.drawable.empty_view_small)
                    .into(imageViewWriteScheReviewThumb)
            }
        }
        viewModel.numOfImages.observe(this@WriteScheduleReviewActivity){ numOfImgs ->
            binding.apply {
                textViewWriteScheReviewPhotoNum.text = getString(R.string.text_num_of_images, numOfImgs)
            }
        }
    }

    private fun settingImageRVAdapter() {
        viewModel.imageUriList.observe(this){ imageUriList ->
            val scheduleReviewImageAdapter = WriteReviewImageAdapter(imageUriList){ position ->
                viewModel.removeReviewImageFromList(position)
            }
            binding.recyclerViewWriteScheReviewPhotos.adapter = scheduleReviewImageAdapter
        }
    }

    private fun settingButtonClickListner(planId: Long){
        binding.apply {
            imageButtonWriteScheReviewPhotoAdd.setOnClickListener {
                if(!viewModel.isMoreImageAttachable()){
                    showSnackBar(it, "사진은 최대 4개까지 첨부할 수 있습니다")
                    return@setOnClickListener
                }

                if (isPhotoPickerAvailable()) {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                } else {
                    checkPermission()
                }
            }

            buttonWriteScheReivewSubmit.setOnClickListener {
                val isValid = isReviewValid()
                if (!isValid) return@setOnClickListener

                val isPrivateOrPublic = buttonGroupWriteScheReviewPublic.checkedRadioButtonId
                val isPublic = when (isPrivateOrPublic) {
                    R.id.radioButtonWriteScheReviewPublic -> true
                    else -> false // isPrivateOrPublic == R.id.radioButtonWriteScheReviewPrivate
                }

                val reviewContent = editTextWriteScheReviewContent.text.toString()
                val reviewRating = ratingbarWriteScheReview.rating

                val reviewDetail = NewScheduleReview(reviewRating, reviewContent, isPublic)

                    viewModel.submitScheduleReview(planId, reviewDetail) { _, requestFlag ->
                        if (requestFlag) {
                            setResult(RESULT_REVIEW_WRITE)
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun saveImageDataAndPath(uri: Uri){
        val imagePath = toAbsolutePath(uri)
        if(imagePath!=null){
            viewModel.addNewReviewImage(uri, imagePath)
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

    private fun isPhotoPickerAvailable(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            true
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            SdkExtensions.getExtensionVersion(Build.VERSION_CODES.R) >= 2
        } else {
            false
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

    private fun isReviewValid(): Boolean {
        with(binding){
            val isPrivateOrPublic = buttonGroupWriteScheReviewPublic.checkedRadioButtonId
            if (isPrivateOrPublic == View.NO_ID) {
                showSnackBar(buttonGroupWriteScheReviewPublic, "여행 일정 공개 범주를 선택해주세요")
                return false
            }

            val reviewContent = editTextWriteScheReviewContent.text.toString()
            if(reviewContent.isBlank()){
                inputLayoutWriteScheReviewContent.error = getString(R.string.text_warning_review_content_empty)
                return false
            }
        }
        return true
    }

    private fun initReviewContentWatcher() {
        with(binding){
            editTextWriteScheReviewContent.addTextChangedListener {
                inputLayoutWriteScheReviewContent.error = null
            }
        }
    }

    private fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(getColor(R.color.text_secondary))
            .show()
    }
}