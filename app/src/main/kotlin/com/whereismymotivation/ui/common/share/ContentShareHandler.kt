package com.whereismymotivation.ui.common.share

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.utils.share.ShareUtils
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun ContentShareHandler(
    sharer: Sharer<Content>
) {
    val context = LocalContext.current
    LaunchedEffect("message") {
        sharer.payload.onEach {
            when (it.type) {
                Payload.Type.TEXT -> {
                    ShareUtils.shareContentAsText(context, it.data, false)
                }

                Payload.Type.IMAGE -> {
                    if (it.bitmap != null)
                        ShareUtils.shareContentAsImage(context, it.data, it.bitmap, false)
                }

                Payload.Type.WHATSAPP_TEXT -> {
                    ShareUtils.shareContentAsText(context, it.data, true)
                }

                Payload.Type.WHATSAPP_IMAGE -> {
                    if (it.bitmap != null)
                        ShareUtils.shareContentAsImage(context, it.data, it.bitmap, true)
                }
            }
        }.launchIn(this)
    }
}