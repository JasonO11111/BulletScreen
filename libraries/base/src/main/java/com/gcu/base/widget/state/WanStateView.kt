package com.gcu.base.widget.state

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.gcu.base.R

class WanStateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : MultiStateView(context, attrs) {

    fun showContent() {
        viewState = ViewState.CONTENT
    }

    fun showError(errorMsg: String) {
        viewState = ViewState.ERROR
        getView(ViewState.ERROR)?.apply {
            val tvError = findViewById<TextView>(R.id.tv_error)
            tvError.text = errorMsg
        }
    }

    fun showLoading() {
        viewState = ViewState.LOADING
    }

    fun showEmpty(emptyMsg: String) {
        viewState = ViewState.EMPTY
        getView(ViewState.EMPTY)?.apply {
            val tvEmpty = findViewById<TextView>(R.id.tv_empty)
            tvEmpty.text = emptyMsg
        }
    }

}