package kr.tekit.lion.daongil.presentation.ext

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.ext.SdkExtensions
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun Context.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.hasImagePermission(): Boolean = Permissions.REQUEST_IMAGE_PERMISSIONS.all { this.hasPermission(it) }

fun Context.hasLocationPermission(): Boolean = Permissions.REQUEST_LOCATION_PERMISSIONS.all { this.hasPermission(it) }

fun Context.showSoftInput(view: View){
    val inputMethodManger = this.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManger.showSoftInput(view, 0)
}

fun Context.showPermissionSnackBar(view: View) {
    Snackbar.make(view, "권한이 거부 되었습니다. 설정(앱 정보)에서 권한을 확인해 주세요.",
        Snackbar.LENGTH_SHORT
    ).setAction("확인"){
        //설정 화면으로 이동
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val packageName = this.packageName
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri

        this.startActivity(intent)

    }.show()
}



fun Context.toAbsolutePath(uri: Uri): String? {
    if (DocumentsContract.isDocumentUri(this, uri)) {
        when {
            uri.isExternalStorageDocument() -> {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return "${getExternalFilesDir(null)?.absolutePath}/${split[1]}"
                }
            }
            uri.isDownloadsDocument() -> {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), id.toLong()
                )
                return getDataColumn(contentUri, null, null)
            }
            uri.isMediaDocument() -> {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]

                val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

                return getDataColumn(contentUri, selection, selectionArgs)
            }
        }
    }
    // MediaStore (and general)
    else if ("content".equals(uri.scheme, ignoreCase = true)) {
        return getDataColumn(uri, null, null)
    }
    // File
    else if ("file".equals(uri.scheme, ignoreCase = true)) {
        return uri.path
    }

    return null
}

fun Context.getDataColumn(uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(column)

    try {
        cursor = uri?.let { this.contentResolver.query(it, projection, selection, selectionArgs, null) }
        if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(index)
        }
    } finally {
        cursor?.close()
    }
    return null
}

fun Context.compressImage(imageUri: Uri): File? {
    val filePath = this.toAbsolutePath(imageUri) ?: return null
    val bitmap = BitmapFactory.decodeFile(filePath)
    val compressedFile = File(this.cacheDir, "my_profile_image.jpg")

    try {
        compressedFile.createNewFile()
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)  // 80% 품질로 압축
        val bitmapData = outputStream.toByteArray()

        val fos = FileOutputStream(compressedFile)
        fos.write(bitmapData)
        fos.flush()
        fos.close()
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }

    return compressedFile
}



