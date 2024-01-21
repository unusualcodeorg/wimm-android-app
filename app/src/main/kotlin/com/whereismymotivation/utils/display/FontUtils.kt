package com.whereismymotivation.utils.display

import androidx.annotation.Keep
import kotlin.random.Random

object FontUtils {

    private val FONT_ARRAY: Array<FontName> = arrayOf(
        FontName.SAIL,
        FontName.AMARANTH,
        FontName.PLAY_TONE_ONE,
        FontName.BERKSHIRE,
        FontName.OLEO_SCRIPT,
        FontName.OSWALD,
    )

    private fun getRandomFont(): FontName {
        val index = Random.nextInt(0, FONT_ARRAY.size)
        return if (index >= 0 && index < FONT_ARRAY.size) FONT_ARRAY[index]
        else FONT_ARRAY[0]
    }

    fun getFont(index: Int): FontName {
        return if (index >= 0 && index < FONT_ARRAY.size) {
            FONT_ARRAY[index]
        } else {
            val rem = index.rem(FONT_ARRAY.size)
            return if (rem >= 0 && rem < FONT_ARRAY.size) FONT_ARRAY[rem]
            else getRandomFont()
        }
    }

    @Keep
    enum class FontName {
        SAIL,
        AMARANTH,
        PLAY_TONE_ONE,
        BERKSHIRE,
        OLEO_SCRIPT,
        OSWALD
    }
}