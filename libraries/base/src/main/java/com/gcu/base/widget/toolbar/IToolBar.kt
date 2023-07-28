package com.gcu.base.widget.toolbar

interface IToolBar {

    /**
     * 标题栏样式
     */
    fun setLeftIconRes(resId: Int)
    fun setLeftIconVisible(isVisible: Boolean)
    fun setRightIconRes(resId: Int)
    fun setRightIconVisible(isVisible: Boolean)
    fun setTitleBoldText(isBold: Boolean)
    fun setTitleVisible(isVisible: Boolean)
    fun setSubTitleBoldText(isBold: Boolean)
    fun setSubTitleVisible(isVisible: Boolean)
    fun setTitle(title: String)
    fun setSubTitle(title: String)
}