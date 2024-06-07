package kr.tekit.lion.daongil.presentation.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ext.SdkExtensions
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kr.tekit.lion.daongil.databinding.ActivityWriteReviewBinding
import kr.tekit.lion.daongil.presentation.home.adapter.WriteReviewImageRVAdapter

class WriteReviewActivity : AppCompatActivity() {
    val binding: ActivityWriteReviewBinding by lazy {
        ActivityWriteReviewBinding.inflate(layoutInflater)
    }
    private val selectedImages: ArrayList<Uri> = ArrayList()
    lateinit var imageRVAdapter : WriteReviewImageRVAdapter

    @SuppressLint("NotifyDataSetChanged")
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            selectedImages.add(uri)
            imageRVAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private val albumLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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


    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            startAlbumLauncher()
        } else {
//            val permissionDialog = PermissionDialog()
//            permissionDialog.isCancelable = false
//            permissionDialog.show(parentFragmentManager, "permission dialog")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        settingToolbar()
        settingImageRVAdapter()
        settingBtn()
    }

    private fun settingToolbar() {
        binding.writeReviewToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun settingImageRVAdapter() {
        imageRVAdapter = WriteReviewImageRVAdapter(selectedImages)
        binding.writeReviewImageRv.adapter = imageRVAdapter
        binding.writeReviewImageRv.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun settingBtn() {
        binding.writeReviewImageAddBtn.setOnClickListener {
            if (isPhotoPickerAvailable()) {
                this.pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                checkPermission()
            }
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
}