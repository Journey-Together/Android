package kr.tekit.lion.daongil.presentation.ext

import android.Manifest
import android.os.Build

object Permissions {
    val REQUEST_IMAGE_PERMISSIONS by lazy{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    val REQUEST_LOCATION_PERMISSIONS by lazy {
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    }

    val LOCATION_PERMISSION_REQUEST_CODE by lazy { 100 }
    val IMAGE_PERMISSION_REQUEST_CODE by lazy { 101 }
}