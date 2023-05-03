package com.harrycampaz.searchproducts.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.*

fun ImageView.loadImgList(url: String){
    Glide.with(this)
        .load(url)
        .into(this)
}

fun Double.toPriceFormat(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "AR"))
    format.maximumFractionDigits = 0
    return format.format(this)
}