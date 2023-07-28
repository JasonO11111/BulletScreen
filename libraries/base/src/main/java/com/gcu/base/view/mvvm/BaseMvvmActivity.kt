package com.gcu.base.view.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.gcu.base.R
import com.gcu.base.widget.state.WanStateView
import com.gcu.common.utils.ToastUtil
import com.therouter.TheRouter
import java.lang.Exception
import java.lang.reflect.ParameterizedType

open abstract class BaseMvvmActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity(),
    IStatusView {

    lateinit var mBinding: VB
    lateinit var mViewModel: VM
    private var mStatView: WanStateView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initViewModel()
        TheRouter.inject(this)
        setContentView(mBinding.root)
        initView()
    }

    private fun initViewBinding() {
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
    }

    private fun initViewModel() {
        mViewModel = getViewModel()!!
        mViewModel.getErrorLiveData().observe(this) {
            showError(it)
        }
        mViewModel.getEmptyLiveData().observe(this) {
            showEmpty(it)
        }
        mViewModel.getLoadingLiveData().observe(this) {
            showLoading(it)
        }
    }

    protected open fun getViewModel(): VM? {
        //当前对象超类的类型
        val type = javaClass.genericSuperclass
        if (type != null && type is ParameterizedType) {
            //返回此类型实际类型参数的对象数据
            val actualTypeArguments = type.actualTypeArguments
            val tClass = actualTypeArguments[1]
            return ViewModelProvider(this).get(tClass as Class<VM>)
        }
        return null
    }

    override fun showEmpty(emptyMsg: String) {
        mStatView?.showEmpty(emptyMsg)
    }

    override fun showError(errMsg: String) {
        mStatView?.showError(errMsg)
    }

    override fun showLoading(isShowLoading: Boolean) {
        mStatView?.showLoading()
    }

    protected open fun initView() {
        mStatView = findViewById(R.id.state_view)
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

    fun isShowStatusBar(isShow: Boolean) {
        if (isShow) {
            
        }
    }
}