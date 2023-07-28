package com.gcu.base.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *
 * @author: zoulongsheng
 * @date: 2023/5/10
 */

fun RecyclerView.verticalList(
    adapter: BaseQuickAdapter<*, BaseViewHolder>,
    isNeedLoadMore: Boolean = true,
    loadMoreMethod: (() -> Unit)? = null
) {
    if (isNeedLoadMore) {
        adapter.loadMoreModule.isAutoLoadMore = true
        adapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = true
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)
        adapter.isAnimationFirstOnly = true
        adapter.loadMoreModule.setOnLoadMoreListener {
            loadMoreMethod?.invoke()
        }
    }
    layoutManager = LinearLayoutManager(context)
    setHasFixedSize(true)
    (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    this.adapter = adapter
}