package com.gcu.base.view.mvvm

interface IStatusView {

    fun showEmpty(emptyMsg: String)

    fun showError(errMsg: String)

    fun showLoading(isShowLoading: Boolean = true)

}