package com.whereismymotivation.utils.share

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.utils.common.Constants
import com.whereismymotivation.utils.common.FileUtils
import com.whereismymotivation.utils.display.Toaster
import com.whereismymotivation.utils.log.Logger
import java.io.File

object ShareUtils {

    private const val TAG = "ShareUtils"

    private const val MIME_TYPE_IMAGE = "image/*"
    private const val MIME_TYPE_TEXT = "text/plain"

    private const val WIMM_APP_IMAGE_SHARE = "wimm_app_image_share"
    private val SHAREABLE_FILENAME: String get() = "Img_${System.currentTimeMillis()}.png"

    private fun shareText(context: Context, text: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = MIME_TYPE_TEXT
        intent.putExtra(Intent.EXTRA_TEXT, text)
        context.startActivity(Intent.createChooser(
            intent,
            context.getString(R.string.share_using)
        ).apply {
            flags = FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun shareTextOnWhatsApp(context: Context, text: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = MIME_TYPE_TEXT
        intent.`package` = "com.whatsapp"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        if (isCallable(context, intent)) {
            context.startActivity(intent)
        } else {
            Toaster.show(context, R.string.whatsapp_not_installed)
            shareText(context, text)
        }
    }

    suspend fun shareContentAsImage(
        context: Context,
        content: Content,
        bitmap: Bitmap,
        whatsapp: Boolean = false,
    ) {

        val desFilePath = context.filesDir.path + File.separator + WIMM_APP_IMAGE_SHARE

        FileUtils.deleteFiles(desFilePath)

        val desFileName = SHAREABLE_FILENAME
        val completeFilePath = desFilePath + File.separator + desFileName
        val saveBitmap = FileUtils.saveBitmap(bitmap, desFilePath, desFileName)

        val message = "Check now: ${content.title} on WhereIsMyMotivation App #motivation"

        if (saveBitmap) {
            val uri =
                FileProvider.getUriForFile(
                    context,
                    Constants.FILE_PROVIDER_AUTHORITY,
                    File(completeFilePath)
                )

            val description =
                "App for motivational videos, talks, quotes and biographies of great people"

            if (whatsapp) {
                shareImageMediaFileOnWhatsApp(context, uri, message)
            } else {
                shareImageMediaFile(context, uri, message)
            }
        } else {
            Toaster.show(context, R.string.string_somethingWentWrong)
            Logger.d(TAG, "Error : while saving bitmap")
        }
    }

    suspend fun shareContentAsText(
        context: Context,
        content: Content,
        whatsapp: Boolean = false,
    ) {
        val text = content.title
        val message = when (content.category) {
            Content.Category.AUDIO -> "Listen now: $text on WhereIsMyMotivation App #motivation"
            Content.Category.VIDEO -> "Watch now: $text on WhereIsMyMotivation App #motivation"
            Content.Category.IMAGE -> "See now: $text on WhereIsMyMotivation App #motivation"
            Content.Category.YOUTUBE -> "Watch now: $text on WhereIsMyMotivation App #motivation"
            Content.Category.FACEBOOK_VIDEO -> "Watch now: $text on WhereIsMyMotivation App #motivation"
            Content.Category.ARTICLE -> "Read now: $text on WhereIsMyMotivation App #motivation"
            Content.Category.QUOTE -> "Read now: $text on WhereIsMyMotivation App #motivation"
            Content.Category.MENTOR_INFO -> "Follow now: $text on WhereIsMyMotivation App #motivation"
            Content.Category.TOPIC_INFO -> "Follow now: $text on WhereIsMyMotivation App #motivation"
        }

        if (whatsapp) {
            shareTextOnWhatsApp(context, message)
        } else {
            shareText(context, message)
        }
    }


    private fun shareImageMediaFile(applicationContext: Context, uri: Uri, text: String) {
        shareMediaFile(applicationContext, uri, text, MIME_TYPE_IMAGE)
    }

    private fun shareImageMediaFileOnWhatsApp(context: Context, mediaFileUri: Uri, text: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = MIME_TYPE_IMAGE
        intent.`package` = "com.whatsapp"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.putExtra(Intent.EXTRA_STREAM, mediaFileUri)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        if (isCallable(context, intent)) {
            context.startActivity(intent)
        } else {
            Toaster.show(context, R.string.whatsapp_not_installed)
            shareImageMediaFile(context, mediaFileUri, text)
        }
    }

    private fun shareMediaFile(
        context: Context,
        mediaFileUri: Uri,
        text: String,
        mimeType: String
    ) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = mimeType
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.putExtra(Intent.EXTRA_STREAM, mediaFileUri)
        context.startActivity(Intent.createChooser(
            intent,
            context.getString(R.string.share_content_using)
        ).apply {
            flags = FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun isCallable(context: Context, intent: Intent): Boolean {
        val list = context.packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        return list.size > 0
    }
}