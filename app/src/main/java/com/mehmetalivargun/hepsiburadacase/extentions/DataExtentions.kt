package com.mehmetalivargun.hepsiburadacase.extentions

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

fun String?.getDate(): String {
    this?.apply {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val parsedDate = sdf.parse(this)
        val systemSdp = SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH)
        return systemSdp.format(parsedDate!!)
    }

    return ""
}

fun ImageView.load(imgUrl: String) {
    if (imgUrl.isEmpty())
        return
    Glide.with(context)
        .load(imgUrl)
        .into(this)
}

