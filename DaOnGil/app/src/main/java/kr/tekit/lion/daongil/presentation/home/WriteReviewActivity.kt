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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityWriteReviewBinding
import kr.tekit.lion.daongil.presentation.ext.showSoftInput
import kr.tekit.lion.daongil.presentation.ext.toAbsolutePath
import kr.tekit.lion.daongil.presentation.home.adapter.WriteReviewImageRVAdapter
import kr.tekit.lion.daongil.presentation.home.vm.WriteReviewViewModel
import kr.tekit.lion.daongil.presentation.home.vm.WriteReviewViewModelFactory
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialogInterface
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class WriteReviewActivity : AppCompatActivity(), ConfirmDialogInterface {
    private val binding: ActivityWriteReviewBinding by lazy {
        ActivityWriteReviewBinding.inflate(layoutInflater)
    }
    private val viewModel: WriteReviewViewModel by viewModels { WriteReviewViewModelFactory() }
    private val selectedImages: ArrayList<Uri> = ArrayList()
    private lateinit var imageRVAdapter: WriteReviewImageRVAdapter

    @SuppressLint("NotifyDataSetChanged")
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                if (selectedImages.size < 4) {
                    selectedImages.add(uri)
                    imageRVAdapter.notifyDataSetChanged()

                    val path = this.toAbsolutePath(uri)
                    viewModel.setReviewImages(path!!)
                } else {
                    Snackbar.make(binding.root, "이미지는 최대 4장까지 첨부 가능합니다", Snackbar.LENGTH_SHORT).show()
                }
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
                    if (selectedImages.size < 4) {
                        // 이미지를 리스트에 추가하고 어댑터에 데이터 변경을 알림
                        selectedImages.add(it)
                        imageRVAdapter.notifyDataSetChanged()

                        val path = this.toAbsolutePath(uri)
                        viewModel.setReviewImages(path!!)
                    } else {
                        Snackbar.make(binding.root, "이미지는 최대 4장까지 첨부 가능합니다", Snackbar.LENGTH_SHORT).show()
                    }
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

        val placeId = intent.getLongExtra("reviewPlaceId", -1)
        val placeName = intent.getStringExtra("reviewPlaceName") ?: "관광지"
        val placeAddress = intent.getStringExtra("reviewPlaceAddress") ?: "관광지 주소"
        val placeImage = intent.getStringExtra("reviewPlaceImage") ?: ""

        settingToolbar()
        settingPlaceData(placeName, placeAddress, placeImage)
        settingImageRVAdapter()
        settingBtn(placeId)
    }

    private fun settingToolbar() {
        binding.writeReviewToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun settingPlaceData(placeName: String, placeAddress: String, placeImage: String) {
        binding.writeReviewTitleTv.text = placeName
        binding.writeReviewAddressTv.text = placeAddress

        Glide.with(binding.writeReviewIv)
            .load(placeImage)
            .placeholder(R.drawable.empty_view)
            .error(R.drawable.empty_view)
            .into(binding.writeReviewIv)
    }

    private fun settingImageRVAdapter() {
        imageRVAdapter = WriteReviewImageRVAdapter(selectedImages) {position ->
            viewModel.deleteImage(position)
        }
        binding.writeReviewImageRv.adapter = imageRVAdapter
        binding.writeReviewImageRv.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun settingBtn(placeId: Long) {
        binding.writeReviewImageAddBtn.setOnClickListener {
            if (isPhotoPickerAvailable()) {
                this.pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                checkPermission()
            }
        }

        binding.writeReviewDateBtn.setOnClickListener {
            setDate()
        }

        binding.writeReviewBtn.setOnClickListener {
            if (checkReviewValid()) {
                val visitDate = viewModel.placeVisitDate.value
                val reviewRating = binding.writeReviewRatingbar.rating
                val reviewText = binding.writeReviewTextWriteEdit.text.toString()

                viewModel.writePlaceReviewData(
                    placeId,
                    visitDate!!,
                    reviewRating,
                    reviewText
                ) { _, requestFlag, code ->
                    if (requestFlag) {
                        this.setResult(Activity.RESULT_OK)
                        this.finish()
                    } else {
                        if (code == 2006) {
                            Snackbar.make(binding.root, "이미 작성한 리뷰입니다", Snackbar.LENGTH_SHORT)
                                .show()
                        } else {
                            Snackbar.make(binding.root, "다시 시도해주세요", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
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

    private fun setDate() {
        val constraintsBuilder = CalendarConstraints.Builder()
        val maxValidator = DateValidatorPointBackward.now()

        constraintsBuilder.setValidator(maxValidator)

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTheme(R.style.DateRangePickerTheme)
            .setTitleText("방문 기간을 설정해주세요")
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        datePicker.show(supportFragmentManager, "WriteReviewDate")
        datePicker.addOnPositiveButtonClickListener {
            val selectedDate = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
            viewModel.setPlaceVisitDate(selectedDate)
            showPickedDates()
        }
    }

    private fun formatDateValue(date: LocalDate): String {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA)

        return dateFormat.format(date)
    }

    private fun showPickedDates() {
        val visitDate = viewModel.placeVisitDate.value
        val visitDateValue = visitDate?.let {
            formatDateValue(visitDate)
        }
        binding.writeReviewDateBtn.text = visitDateValue
    }

    private fun checkReviewValid(): Boolean {
        val date = viewModel.placeVisitDate.value
        val reviewText = binding.writeReviewTextWriteEdit.text.toString()

        return if (date == null) {
            Snackbar.make(binding.root, "방문 날짜를 선택해 주세요", Snackbar.LENGTH_SHORT).show()
            false

        } else if (reviewText.isEmpty()) {
            binding.writeReviewTextWriteEdit.requestFocus()
            this.showSoftInput(binding.writeReviewTextWriteEdit)
            Snackbar.make(binding.root, "방문 날짜를 선택해 주세요", Snackbar.LENGTH_SHORT).show()

            false

        } else {
            true
        }
    }
}