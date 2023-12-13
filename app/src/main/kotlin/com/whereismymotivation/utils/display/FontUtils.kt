package com.whereismymotivation.utils.display

import com.whereismymotivation.R
import kotlin.random.Random

object FontUtils {

    private val FONT_ARRAY: Array<Int> = arrayOf(
        R.font.sail_regular,
        R.font.amaranth_regular,
        R.font.paytone_one_regular,
        R.font.berkshire_swash_regular,
        R.font.oleo_script_regular,
        R.font.oswald_regular
    )

    fun getRandomFont(): Int {
        val index = Random.nextInt(0, FONT_ARRAY.size)
        return if (index >= 0 && index < FONT_ARRAY.size) FONT_ARRAY[index]
        else FONT_ARRAY[0]
    }

    fun getFont(index: Int): Int {
        return if (index >= 0 && index < FONT_ARRAY.size) {
            FONT_ARRAY[index]
        } else {
            val rem = index.rem(FONT_ARRAY.size)
            return if (rem >= 0 && rem < FONT_ARRAY.size) FONT_ARRAY[rem]
            else getRandomFont()
        }
    }
}