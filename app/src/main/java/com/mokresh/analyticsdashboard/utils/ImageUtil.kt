package com.mokresh.analyticsdashboard.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


object ImageUtil {

    // to render the image and show it using Glide
    fun renderImage(photoUrl: String?, imageView: ImageView, placeholder: Int, context: Context) {
        if (photoUrl == "" || photoUrl == null) {
            Glide.with(context).load(placeholder).fitCenter().centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView)
        } else {
            Glide.with(context).load(photoUrl).placeholder(placeholder).error(placeholder)
                .fitCenter().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView)
        }

    }

}