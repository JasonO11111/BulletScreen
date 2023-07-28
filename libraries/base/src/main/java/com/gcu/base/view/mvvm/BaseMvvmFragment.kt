package com.gcu.base.view.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.gcu.base.R
import com.gcu.base.widget.state.WanStateView
import com.therouter.TheRouter
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

open class BaseMvvmFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment(), IStatusView {

    private var _bind: VB? = null
    protected val mBinding get() = _bind!!
    lateinit var mViewModel: VM
    private var mStatView: WanStateView? = null
    private var mIsFirstLoadData = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        try {
            val vmClass =
                (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
            val method: Method = vmClass.getMethod(
                "inflate",
                LayoutInflater::class.java,
            )
            _bind = method.invoke(null, layoutInflater) as VB
            TheRouter.inject(this)
            return mBinding.root
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView(view)
        if (mIsFirstLoadData) {
            onLoadData()
        }
    }

    protected open fun initView(view: View) {
        mStatView = view.findViewById(R.id.state_view)
    }

    private fun initViewModel() {
        mViewModel = getViewModel()!!
        mViewModel.getErrorLiveData().observe(viewLifecycleOwner) {
            showError(it)
        }
        mViewModel.getEmptyLiveData().observe(viewLifecycleOwner) {
            showEmpty(it)
        }
        mViewModel.getLoadingLiveData().observe(viewLifecycleOwner) {
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

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
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

    override fun onResume() {
        super.onResume()
        mIsFirstLoadData = false
    }

    protected open fun onLoadData() {

    }
}