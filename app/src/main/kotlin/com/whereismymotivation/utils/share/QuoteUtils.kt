package com.whereismymotivation.utils.share

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.whereismymotivation.R
import com.whereismymotivation.utils.common.Constants
import com.whereismymotivation.utils.display.FontUtils
import com.whereismymotivation.utils.log.Logger

object QuoteUtils {

    @SuppressLint("InflateParams")
    fun getShareView(context: Context, author: String, quote: String, image: Bitmap): View? {
        try {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.layout_quote_notification, null)
            val tvQuote = view.findViewById<TextView>(R.id.tv_quote)
            val tvAuthor = view.findViewById<TextView>(R.id.tv_author)
            val ivThumbnail = view.findViewById<ImageView>(R.id.iv_thumbnail)

            tvQuote.text = quote
            tvAuthor.text = author
            ivThumbnail.setImageBitmap(image)

            FontUtils.getFont(quote.length)
                .let {
                    if (it != Constants.NULL_INDEX)
                        try {
                            ResourcesCompat.getFont(context, it)?.run {
                                tvQuote.typeface = this
                            }
                        } catch (e: Exception) {
                            Logger.record(e)
                        }
                }
            return view
        } catch (e: Exception) {
            Logger.record(e)
            return null
        }
    }
}