package com.gcu.base.widget.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gcu.base.R
import com.gcu.base.utils.visibleOrGone

/**
 * 标题栏
 */
class ActionToolBar : LinearLayout, IToolBar {

    private val mIvBack by lazy {
        findView<ImageView>(R.id.iv_back)
    }
    private val mIvDelete by lazy {
        findView<ImageView>(R.id.iv_delete)
    }
    private val mTvTitle by lazy {
        findView<TextView>(R.id.tv_title)
    }
    private val mTvSubTitle by lazy {
        findView<TextView>(R.id.tv_sub_title)
    }
    private var mClickListener: OnClickToolbarListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleArr: Int) : super(
        context, attrs, defStyleArr
    ) {
        init()
    }

    fun setToolbarClickListener(listener: OnClickToolbarListener) {
        mClickListener = listener
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.layout_action_tool_bar, this, true)
        mIvBack.setOnClickListener {
            mClickListener?.onClickLeftIcon()
        }
        mIvDelete.setOnClickListener {
            mClickListener?.onClickRightIcon()
        }
        mTvTitle.setOnClickListener {
            mClickListener?.onClickCenterText()
        }
        mTvSubTitle.setOnClickListener {
            mClickListener?.onClickSubCenterText()
        }
    }

    override fun setLeftIconRes(resId: Int) {
        mIvBack.setImageResource(resId)
    }

    override fun setLeftIconVisible(isVisible: Boolean) {
        mIvBack.visibleOrGone(isVisible)
    }

    override fun setRightIconRes(resId: Int) {
        mIvDelete.setImageResource(resId)
    }

    override fun setRightIconVisible(isVisible: Boolean) {
        mIvDelete.visibleOrGone(isVisible)
    }

    override fun setTitleBoldText(isBold: Boolean) {
        mTvTitle.paint.isFakeBoldText = isBold
    }

    override fun setTitleVisible(isVisible: Boolean) {
        mTvTitle.visibleOrGone(isVisible)
    }

    override fun setSubTitleBoldText(isBold: Boolean) {
        mTvSubTitle.paint.isFakeBoldText = isBold
    }

    override fun setSubTitleVisible(isVisible: Boolean) {
        mTvSubTitle.visibleOrGone(isVisible)
    }

    override fun setTitle(title: String) {
        mTvTitle.text = title
    }

    override fun setSubTitle(title: String) {
        mTvSubTitle.text = title
    }

    private fun <T : View> findView(resId: Int): T {
        return findViewById(resId)
    }

    interface OnClickToolbarListener {
        /**
         * 事件点击
         */
        fun onClickLeftIcon()
        fun onClickCenterText()
        fun onClickSubCenterText()
        fun onClickRightIcon()
    }
}