package com.ryz.movie.core.utils

import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ryz.movie.BuildConfig.IMAGE_URL
import com.ryz.movie.R
import kotlinx.coroutines.delay

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

suspend fun ViewPager2.scrollIndefinitely(interval: Long) {
    delay(interval)
    val numberOfItems = adapter?.itemCount ?: 0
    val lastIndex = if (numberOfItems > 0) numberOfItems - 1 else 0
    val nextItem = if (currentItem == lastIndex) 0 else currentItem + 1

    setCurrentItem(nextItem, true)

    scrollIndefinitely(interval)
}

fun ViewPager2.autoScroll(lifecycleScope: LifecycleCoroutineScope, interval: Long) {
    lifecycleScope.launchWhenResumed {
        scrollIndefinitely(interval)
    }
}

fun ViewPager2.setCarouselEffects(){
    offscreenPageLimit = 1

    val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
    val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
    val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
    val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
        page.translationX = -pageTranslationX * position
        page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
        page.alpha = 0.5f + (1 - kotlin.math.abs(position))
    }
    setPageTransformer(pageTransformer)
}
