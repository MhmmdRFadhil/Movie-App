package com.ryz.movie.core.utils

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ryz.movie.BuildConfig.IMAGE_URL
import com.ryz.movie.R

fun ImageView.loadImageUrl(url: String) {
    Glide.with(this.context)
        .load(IMAGE_URL + url)
        .transform(RoundedCorners(25))
        .error(R.drawable.ic_baseline_broken_image_24)
        .apply(RequestOptions.placeholderOf(R.drawable.ic_baseline_refresh_24))
        .into(this)
}

// custom Toolbar
fun AppCompatActivity.simpleToolbar(
    toolbarTitle: String,
    toolbar: Toolbar?,
    navigationIcon: Boolean
) {
    setSupportActionBar(toolbar)
    supportActionBar?.apply {
        title = toolbarTitle
        setDisplayHomeAsUpEnabled(navigationIcon)
    }
}