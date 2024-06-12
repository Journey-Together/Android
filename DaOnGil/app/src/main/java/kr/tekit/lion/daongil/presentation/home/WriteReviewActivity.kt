package kr.tekit.lion.daongil.presentation.home

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
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityWriteReviewBinding
import kr.tekit.lion.daongil.presentation.home.adapter.WriteReviewImageRVAdapter
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialogInterface
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WriteReviewActivity : AppCompatActivity(), ConfirmDialogInterface {
    val binding: ActivityWriteReviewBinding by lazy {
        ActivityWriteReviewBinding.inflate(layoutInflater)
    }
    private val selectedImages: ArrayList<Uri> = ArrayList()
    lateinit var imageRVAdapter: WriteReviewImageRVAdapter

    @SuppressLint("NotifyDataSetChanged")
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                selectedImages.add(uri)
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
                    selectedImages.add(it)
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
                permissionDialog.show(supportFragmentManager, "PermissionDialog")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        settingToolbar()
        settingImageRVAdapter()
        settingBtn()
        settingPlaceData(binding)
    }

    private fun settingToolbar() {
        binding.writeReviewToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun settingImageRVAdapter() {
        imageRVAdapter = WriteReviewImageRVAdapter(selectedImages)
        binding.writeReviewImageRv.adapter = imageRVAdapter
        binding.writeReviewImageRv.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun settingBtn() {
        binding.writeReviewImageAddBtn.setOnClickListener {
            if (isPhotoPickerAvailable()) {
                this.pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                checkPermission()
            }
        }

        binding.writeReviewDateBtn.setOnClickListener {
            setDate(binding)
        }

        binding.writeReviewBtn.setOnClickListener {
            finish()
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
    fun checkPermission() {
        val permissionReadExternal = android.Manifest.permission.READ_EXTERNAL_STORAGE

        val permissionReadExternalGranted = ContextCompat.checkSelfPermission(
            applicationContext,
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

    private fun setDate(binding: ActivityWriteReviewBinding) {
        val datePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTheme(R.style.DateRangePickerTheme)
            .setTitleText("방문 기간을 설정해주세요")
            .build()

        datePicker.show(supportFragmentManager, "WriteReviewDate")
        datePicker.addOnPositiveButtonClickListener {
            val startDate = Date(it.first)
            val endDate = Date(it.second)
            showPickedDates(binding, startDate, endDate)
        }
    }

    private fun formatDateValue(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        val formattedDate = dateFormat.format(date)

        return formattedDate
    }

    private fun showPickedDates(binding: ActivityWriteReviewBinding, startDate: Date, endDate: Date) {
//        val startDate = scheduleFormViewModel.startDate.value
//        val endDate = scheduleFormViewModel.endDate.value
        val startDateFormatted = startDate?.let {
            formatDateValue(startDate)
        }
        val endDateFormatted = endDate?.let {
            formatDateValue(endDate)
        }
        binding.writeReviewDateBtn.text = getString(R.string.picked_dates, startDateFormatted, endDateFormatted)
    }

    private fun settingPlaceData(binding: ActivityWriteReviewBinding) {
        val placeName = intent.getStringExtra("placeName")
        val placeAddress = intent.getStringExtra("placeAddress")
        val placeImg = intent.getStringExtra("placeImg")

        binding.writeReviewTitleTv.text = placeName
        binding.writeReviewAddressTv.text = placeAddress

        Glide.with(binding.writeReviewIv.context)
            .load(placeImg)
            .error(R.drawable.empty_view)
            .into(binding.writeReviewIv)
    }
}
