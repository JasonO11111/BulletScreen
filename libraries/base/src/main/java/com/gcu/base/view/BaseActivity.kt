package com.gcu.base.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gcu.common.utils.ToastUtil
import com.therouter.TheRouter
import java.lang.Exception
import java.lang.reflect.ParameterizedType

/**
 * Activity基类
 * @param VB : ViewBinding
 * @property mBinding VB
 */
open abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var mBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //返回当前type --->  BaseActivity
        val type = this.javaClass.genericSuperclass as ParameterizedType
        try {
            //获取泛型中的实际类型
            val clazz = type.actualTypeArguments[0] as Class<*>
            //反射inflate
            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            mBinding = method.invoke(null, layoutInflater) as VB
        } catch (e: Exception) {
            e.printStackTrace()
        }
        setContentView(mBinding.root)
        TheRouter.inject(this)
        initView()
    }

    protected open fun initView() {

    }

    /**
     * 长时间toast
     * @param message
     */
    protected fun showLongToast(message: String) {
        ToastUtil.toastLongMessage(message)
    }

    /**
     * 短时间toast
     * @param message
     */
    protected fun showShortToast(message: String) {
        ToastUtil.toastShortMessage(message)
    }

    /**
     * 消息提示框
     * @param title
     * @param content
     */
    protected fun showMessageDialog(title: String, content: String) {

    }

    protected fun dismissMessageDialog() {

    }

    protected fun showLoadingDialog() {

    }

    protected fun dismissLoadingDialog() {

    }

}