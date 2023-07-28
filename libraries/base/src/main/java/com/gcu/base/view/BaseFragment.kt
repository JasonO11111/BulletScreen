package com.gcu.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.therouter.TheRouter
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

/**
 * fragment基类
 * @param VB : ViewBindings
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _bind: VB? = null
    protected val mBinding get() = _bind!!

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            val vmClass =
                (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
            val method: Method = vmClass.getMethod("inflate", LayoutInflater::class.java)
            _bind = method.invoke(null, layoutInflater) as VB
            return mBinding.root
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        TheRouter.inject(this)
        return null
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}
