package com.gcu.base.widget.recyclerview

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView

/**
 *  左右滑动出现item
 * @author: zoulongsheng
 * @date: 2023/5/12
 */
class SwipeRecyclerview @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    //滑动
    private val mScroller = Scroller(context)

    //当前选中item
    private var mItem: View? = null

    //上次按下的横坐标
    private var mLastX = 0f

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        when (e.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mItem = getSelectItem(e)
                mLastX = e.x
            }

            MotionEvent.ACTION_MOVE -> {
                playMoveItemAnim(e)
            }

            MotionEvent.ACTION_CANCEL -> {

            }

            MotionEvent.ACTION_UP -> {

            }
        }
        return super.onInterceptTouchEvent(e)
    }

    /**
     * 获取选中的item项
     * 1.隐藏的跳过无需判断
     */
    private fun getSelectItem(e: MotionEvent): View? {
        val frame = Rect()
        forEach {
            if (it.visibility != GONE) {
                it.getHitRect(frame)
                if (frame.contains(e.x.toInt(), e.y.toInt())) {
                    return it
                }
            }
        }
        return null
    }

    private fun playMoveItemAnim(e: MotionEvent) {

    }
}