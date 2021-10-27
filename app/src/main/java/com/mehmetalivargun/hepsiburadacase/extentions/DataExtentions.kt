package com.mehmetalivargun.hepsiburadacase.extentions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mehmetalivargun.hepsiburadacase.R
import java.text.SimpleDateFormat
import java.util.*

fun String?.getDate(context: Context):String
{
    this?.apply {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val parsedDate = sdf.parse(this)
        val systemSdp = SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH)
        val formattedDate=systemSdp.format(parsedDate)
        return formattedDate
    }

    return  ""
}

fun ImageView.load(imgUrl: String) {
    Glide.with(context)
        .load(imgUrl)
        .into(this)
}

