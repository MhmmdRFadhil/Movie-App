package com.ryz.movie.core.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.abs
import kotlin.math.min


internal class ZoomCenterLinearLayoutManager(
    context: Context?,
    orientation: Int,
    reverseLayout: Boolean
) : LinearLayoutManager(
    context,
    orientation,
    reverseLayout
) {

    override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
        lp.width = (width / 1.5).toInt()
        return true
    }

    override fun onLayoutCompleted(state: RecyclerView.State) {
        super.onLayoutCompleted(state)
        scaleMiddle()
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler, state: RecyclerView.State): Int {
        return if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            scaleMiddle()
            scrolled
        } else {
            0
        }
    }

    private fun scaleMiddle() {
        val midpoint = width / 2f
        val d1 = 0.9f * midpoint
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child != null) {
                val childMid = (getDecoratedRight(child) + getDecoratedLeft(child)) / 2f
                val d = min(d1, abs(midpoint - childMid))
                val scale = 1 - 0.15f * d / d1
                child.scaleX = scale
                child.scaleY = scale
            }
        }
    }
}