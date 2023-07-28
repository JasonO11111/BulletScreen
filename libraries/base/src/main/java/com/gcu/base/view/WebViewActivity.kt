package com.gcu.base.view

import android.view.ViewGroup
import android.webkit.WebView
import androidx.core.content.ContextCompat
import com.gcu.base.R
import com.gcu.base.config.BundleKey
import com.gcu.base.config.RouteConfig
import com.gcu.base.databinding.ActivityWebViewBinding
import com.gcu.base.view.mvvm.BaseMvvmActivity
import com.gcu.base.view.mvvm.BaseViewModel
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebSettingsImpl
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.therouter.router.Route

/**
 *
 * @author: zoulongsheng
 * @date: 2023/5/5
 */
@Route(path = RouteConfig.WEB_VIEW_ACTIVITY)
class WebViewActivity : BaseMvvmActivity<ActivityWebViewBinding, BaseViewModel>() {

    private var id = 0
    private var title = ""
    private var url = ""
    private val mAgentWeb by lazy {
        AgentWeb.with(this).setAgentWebParent(
            mBinding.llRootView, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
        ).useDefaultIndicator(ContextCompat.getColor(this, R.color.color_blue), 3)
            .interceptUnkownUrl().setAgentWebWebSettings(AgentWebSettingsImpl.getInstance())
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            /**
             * 直接打开跳转页 DERECT
             * 咨询用户是否打开 ASK
             * 禁止打开其他页面 DISALLOW
             */
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
            .setWebChromeClient(object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    mBinding.tvTitle.text = title
                    super.onReceivedTitle(view, title)
                }
            }).createAgentWeb().ready().get()
    }

    override fun initView() {
        super.initView()
        parseData()
        mBinding.ivBack.setOnClickListener {
            finish()
        }
        mAgentWeb.webCreator?.webView?.run {
            overScrollMode = WebView.OVER_SCROLL_NEVER
            settings.run {
                javaScriptCanOpenWindowsAutomatically = false
                loadsImagesAutomatically = true
                useWideViewPort = true
                loadWithOverviewMode = true
            }
        }
        mAgentWeb.urlLoader?.loadUrl(url)
    }

    private fun parseData() {
        val bundle = intent.getBundleExtra(BundleKey.DATA)
        bundle?.run {
            title = getString(BundleKey.TITLE, "")
            url = getString(BundleKey.URL, "")
            id = getInt(BundleKey.ID, 0)
        }
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }
}