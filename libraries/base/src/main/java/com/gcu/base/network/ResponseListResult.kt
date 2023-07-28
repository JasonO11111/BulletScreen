package com.gcu.base.network

import java.io.Serializable

/**
 *
 */
data class ResponseListResult<T>(
    val offset: Int,
    val size: Int,
    val total: Int,
    val pageCount: Int,
    val curPage: Int,
    val over: Boolean,
    val datas: T
) : Serializable