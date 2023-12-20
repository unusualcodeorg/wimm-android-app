package com.whereismymotivation.ui.common.share

import android.graphics.Bitmap

data class Payload<T> private constructor(
    val type: Type,
    val data: T,
    val bitmap: Bitmap? = null
) {

    companion object {
        fun <T> text(data: T) = Payload(Type.TEXT, data)

        fun <T> image(data: T) = Payload(Type.IMAGE, data)

        fun <T> whatsappText(data: T) = Payload(Type.WHATSAPP_TEXT, data)

        fun <T> whatsappImage(data: T) = Payload(Type.WHATSAPP_IMAGE, data)
    }

    enum class Type {
        TEXT,
        IMAGE,
        WHATSAPP_TEXT,
        WHATSAPP_IMAGE
    }
}