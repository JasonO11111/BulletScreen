package com.gcu.base.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.gcu.base.config.BundleKey
import com.gcu.base.config.RouteConfig
import com.therouter.TheRouter

object WanNavigation {

    fun startActivity(path: String) {
        TheRouter.build(path).navigation()
    }

    fun getFragment(path: String) = TheRouter.build(path).createFragment<Fragment>()

    fun startUrl(bundle: Bundle) {
        TheRouter.build(RouteConfig.WEB_VIEW_ACTIVITY).withBundle(BundleKey.DATA, bundle)
            .navigation()
    }
}