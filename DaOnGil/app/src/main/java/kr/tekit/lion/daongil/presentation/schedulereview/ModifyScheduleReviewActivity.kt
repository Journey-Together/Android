package kr.tekit.lion.daongil.presentation.schedulereview

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ext.SdkExtensions
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityModifyScheduleReviewBinding
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialogInterface

class ModifyScheduleReviewActivity : AppCompatActivity(), ConfirmDialogInterface {

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
//                saveImageDataAndPath(uri)
            }
        }

    private val albumLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // 사진 선택을 완료한 후 돌아왔다면
            if (result.resultCode == Activity.RESULT_OK) {
                // 선택한 이미지의 Uri 가져오기
                val uri = result.data?.data
                uri?.let {
//                    saveImageDataAndPath(uri)
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
        setContentView(R.layout.activity_modify_schedule_review)
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
        // TO DO
//        albumLauncher.launch(albumIntent)
    }

    override fun onPosBtnClick() {
        // 앱 설정 화면으로 이동
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

}