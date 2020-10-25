package com.tunc.easynav

import android.content.Context
import androidx.fragment.app.Fragment
import com.android.easynav.src.BaseController
import com.android.easynav.src.FragmentController
import com.android.easynav.src.FragmentOption
import com.android.easynav.src.findController

abstract class BaseFragment : Fragment() {

    lateinit var navigator: FragmentController
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) {
            navigator = context.navigator
        }
    }

    open fun mainNavigate(fragment: Fragment, block: FragmentOption.Builder.() -> Unit = {}) {
        navigate<MainController>(fragment, block)
    }

    open fun childNavigate(fragment: Fragment, block: FragmentOption.Builder.() -> Unit = {}) {
        navigate<ChildController>(fragment, block)
    }

    inline fun <reified T : BaseController> navigate(
        fragment: Fragment,
        block: FragmentOption.Builder.() -> Unit = {}
    ) {
        navigator.navigate(findController<T>(fragment, block))
    }
}