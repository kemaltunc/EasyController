package com.tunc.easynav

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.easynav.src.FragmentController
import com.android.easynav.src.FragmentOption
import com.android.easynav.src.navigateExt

abstract class BaseFragment : Fragment() {

    lateinit var navigator: FragmentController
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) {
            navigator = context.navigator
        }
    }

    open fun mainNavigate(fragment: Fragment, block: FragmentOption.Builder.() -> Unit = {}) {
        navigator.navigateExt<MainController>(fragment, block)
    }

    open fun childNavigate(fragment: Fragment, block: FragmentOption.Builder.() -> Unit = {}) {
        navigator.navigateExt<ChildController>(fragment, block)
    }


    override fun onDestroyView() {
        val parentViewGroup = view?.parent as ViewGroup?
        parentViewGroup?.removeAllViews()
        super.onDestroyView()
    }
}