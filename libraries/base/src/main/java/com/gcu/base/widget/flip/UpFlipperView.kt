package com.gcu.base.widget.flip

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ViewFlipper
import com.gcu.base.R

/**
 * 向上翻转View
 * @author: zoulongsheng
 * @date: 2023/6/6
 */
class UpFlipperView : ViewFlipper {

    companion object {
        /**
         * 默认翻转时间
         */
        private const val DEFAULT_FLIP_INTERVAL = 3000

        /**
         * 动画翻转时间
         */
        private const val DEFAULT_ANIM_DURATION = 500L
    }

    private var mItemClickListener: OnItemClickListener? = null

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

    }

    init {
        flipInterval = DEFAULT_FLIP_INTERVAL
        val animIn = AnimationUtils.loadAnimation(context, R.anim.anim_flip_in)
        val animOut = AnimationUtils.loadAnimation(context, R.anim.anim_flip_out)
        animIn.duration = DEFAULT_ANIM_DURATION
        animOut.duration = DEFAULT_ANIM_DURATION
        inAnimation = animIn
        outAnimation = animOut
    }

    /**
     * 设置翻转view
     */
    fun setViews(views: List<View>) {
        if (views.isEmpty()) {
            return
        }
        removeAllViews()
        views.forEachIndexed { index, view ->
            view.setOnClickListener {
                mItemClickListener?.onClick(index, view)
            }
            val viewParent = view.parent as? ViewGroup
            viewParent?.removeAllViews()
            addView(view)
        }
        startFlipping()
    }

    fun setAnimation(animIn: Animation, animOut: Animation) {
        animIn.duration = DEFAULT_ANIM_DURATION
        outAnimation.duration = DEFAULT_ANIM_DURATION
        inAnimation = animIn
        outAnimation = animOut
    }

    fun setAnimation(animInRes: Int, animOutRes: Int) {
        val animIn = AnimationUtils.loadAnimation(context, animInRes)
        val animOut = AnimationUtils.loadAnimation(context, animOutRes)
        animIn.duration = DEFAULT_ANIM_DURATION
        animOut.duration = DEFAULT_ANIM_DURATION
        inAnimation = animIn
        outAnimation = animOut
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mItemClickListener = listener
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopFlipping()
        mItemClickListener = null
        removeAllViews()
    }

    interface OnItemClickListener {
        fun onClick(pos: Int, view: View)
    }
}