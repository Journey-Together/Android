package kr.tekit.lion.daongil.presentation.schedulereview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ext.SdkExtensions
import android.provider.Settings
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityWriteScheduleReviewBinding
import kr.tekit.lion.daongil.presentation.home.adapter.WriteReviewImageRVAdapter
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialogInterface
import kr.tekit.lion.daongil.presentation.schedulereview.customview.ReviewPublicDialog

class WriteScheduleReviewActivity : AppCompatActivity() ,ConfirmDialogInterface {
    private val selectedImages: ArrayList<Uri> = ArrayList()
    private lateinit var scheduleImageRVAdapter: WriteReviewImageRVAdapter

    @SuppressLint("NotifyDataSetChanged")
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                selectedImages.add(uri)
                scheduleImageRVAdapter.notifyDataSetChanged()
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
                    selectedImages.add(it)
                    scheduleImageRVAdapter.notifyDataSetChanged()
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

        val planId = intent.getIntExtra("planId", -1)

        initToolbar()
        initView()
        settingImageRVAdapter()
        settingButtonClickListner()
    }

    private fun initToolbar(){
        binding.toolbarWriteScheReview.setNavigationOnClickListener {
                finish()
        }
    }
    private fun initView(){
        binding.apply {
            textViewWriteScheReviewName.text = "일정 제목"
            textViewWriteScheReviewPeriod.text = getString(R.string.text_schedule_period, "2024.01.01", "2024.01.02")
//            imageViewWriteScheReviewThumb
        }
    }

    private fun settingImageRVAdapter() {
        scheduleImageRVAdapter = WriteReviewImageRVAdapter(selectedImages) {
            // 이미지 삭제 시
        }
        binding.recyclerViewWriteScheReviewPhotos.adapter = scheduleImageRVAdapter
        binding.recyclerViewWriteScheReviewPhotos.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun settingButtonClickListner(){
        binding.apply {
            imageButtonWriteScheReviewPhotoAdd.setOnClickListener {
                if (isPhotoPickerAvailable()) {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                } else {
                    checkPermission()
                }
            }

            buttonWriteScheReivewSubmit.setOnClickListener {
                val reviewPublicDialog = ReviewPublicDialog{ isPublic ->
                    // 데이터 서버로 전송
//                    binding.apply {
//                        val reviewContent = editTextWriteScheReviewContent.text.toString()
//                        val reviewRating = ratingbarWriteScheReview.rating
//                        selectedImages (첨부이미지), isPublic (공개여부)
//                    }
                    finish()
                }
                reviewPublicDialog.isCancelable = false
                reviewPublicDialog.show(supportFragmentManager, "ReviewPublicDialog")
            }
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
}