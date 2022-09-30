package com.kerite.fission.android

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import androidx.core.animation.addListener
import com.kerite.fission.R

class AnimationProgressBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ProgressBar(context, attributeSet, defStyleAttr, defStyleRes) {
    private var animator: Animator? = null
    var animationDuration: Long
    var animationInterpolator: TimeInterpolator

    init {
        context.obtainStyledAttributes(
            attributeSet, R.styleable.AnimationProgressBar, defStyleAttr, defStyleRes
        ).apply {
            animationDuration = getInteger(
                R.styleable.AnimationProgressBar_animationDuration,
                resources.getInteger(android.R.integer.config_mediumAnimTime)
            ).toLong()
            recycle()
        }
        animationInterpolator = LinearInterpolator()
    }

    @Suppress("unused")
    fun animateTo(progress: Int) {
        animator?.cancel()
        animator = ValueAnimator.ofInt(this.progress, progress).apply {
            duration = animationDuration
            interpolator = animationInterpolator
            addUpdateListener {
                setProgress(it.animatedValue as Int)
            }
            addListener(onEnd = {
                animator = null
            })
        }
    }
}