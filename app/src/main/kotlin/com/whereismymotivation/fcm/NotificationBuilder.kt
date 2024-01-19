package com.whereismymotivation.fcm

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.RingtoneManager
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.whereismymotivation.analytics.Tracker
import com.whereismymotivation.di.ScopeMain
import com.whereismymotivation.utils.log.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@ServiceScoped
class NotificationBuilder @Inject constructor(
    @ScopeMain private val scope: CoroutineScope,
    @ApplicationContext private val context: Context,
    private val tracker: Tracker
) {

    companion object {
        // TODO
    }

    fun showFcmNotification(data: Map<String, String>) {
        // TODO
    }

    fun showFcmNotification(messageBody: String) {
        // TODO
    }

    private fun downloadAndShowImageNotification(
        data: Map<String, String>,
        handler: (data: Map<String, String>, image: Bitmap) -> Unit
    ) {
        val thumbnail = getString(data, "thumbnail", null) ?: return
        scope.launch {
            try {
                val loader = ImageLoader(context)
                val request = ImageRequest.Builder(context)
                    .data(thumbnail)
                    .allowHardware(true)
                    .build()

                val result = (loader.execute(request) as SuccessResult).drawable
                val bitmap = (result as BitmapDrawable).bitmap
                handler(data, bitmap)
            } catch (e: Throwable) {
                Logger.record(e)
            }
        }
    }


    private fun getString(data: Map<String, String>, tag: String, default: String?): String? =
        data[tag] ?: default

    private fun getDefaultSoundUri() =
        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

}