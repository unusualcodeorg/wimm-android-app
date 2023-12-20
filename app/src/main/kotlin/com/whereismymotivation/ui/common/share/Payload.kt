package com.whereismymotivation.ui.common.share

import android.graphics.Bitmap

data class Payload<T> private constructor(
    val type: Type,
    val data: T,
    val bitmap: Bitmap? = null
) {

    companion object {
        fun <T> text(data: T) = Payload(Type.TEXT, data)

        fun <T> image(data: T, bitmap: Bitmap) = Payload(Type.IMAGE, data, bitmap)

        fun <T> whatsappText(data: T) = Payload(Type.WHATSAPP_TEXT, data)

        fun <T> whatsappImage(data: T, bitmap: Bitmap) = Payload(Type.WHATSAPP_IMAGE, data, bitmap)
    }

    enum class Type {
        TEXT,
        IMAGE,
        WHATSAPP_TEXT,
        WHATSAPP_IMAGE
    }
}