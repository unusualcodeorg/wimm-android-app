package com.whereismymotivation.utils.common

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.RawRes
import com.whereismymotivation.utils.log.Logger
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.StringWriter
import java.io.UnsupportedEncodingException

object FileUtils {

    const val TAG = "FileUtils"

    suspend fun getImageSize(file: File): Pair<Int, Int>? {
        return try {
            // Decode image size
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            BitmapFactory.decodeStream(FileInputStream(file), null, o)
            Pair(o.outWidth, o.outHeight)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    suspend fun readRawFile(context: Context, @RawRes rawResId: Int): String {
        try {
            val inputStream = context.resources.openRawResource(rawResId)
            val writer = StringWriter()
            val buffer = CharArray(1024)
            try {
                val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
                var readBuffer = reader.read(buffer)
                while (readBuffer != -1) {
                    writer.write(buffer, 0, readBuffer)
                    readBuffer = reader.read(buffer)
                }
                return writer.toString()
            } finally {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    Logger.e(TAG, e.toString())
                }
            }
        } catch (e: Resources.NotFoundException) {
            throw e
        } catch (e: UnsupportedEncodingException) {
            throw e
        } catch (e: IOException) {
            throw e
        } catch (e: IndexOutOfBoundsException) {
            throw e
        }
    }


    suspend fun deleteFiles(desFilePath: String) {
        val someDir = File(desFilePath)
        someDir.deleteRecursively()
    }

    suspend fun saveBitmap(bitmap: Bitmap, desFilePath: String, desFileName: String): Boolean {
        try {
            if (makeDirs(desFilePath)) {
                if (createFile(desFilePath, desFileName)) {
                    FileOutputStream(File(desFilePath, desFileName)).use { out ->
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                    }
                    return true
                }
            }
            return false
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
    }

    private fun createFile(desFilePath: String, desFileName: String): Boolean {
        val file = File(desFilePath, desFileName)

        return if (file.exists()) {
            true
        } else {
            try {
                file.createNewFile()
            } catch (ex: IOException) {
                false
            }
        }

    }

    private fun makeDirs(filePath: String): Boolean {

        if (filePath.isEmpty()) {
            return false
        }

        val folder = File(filePath)
        return if (folder.exists() && folder.isDirectory) true else folder.mkdirs()
    }

    fun getAppRootDirectoryPath(context: Context): String = context.filesDir.absolutePath

    fun getNotificationDirectoryPath(context: Context): String =
        context.filesDir.path + File.separator + Constants.NOTIFICATION_DIR

}