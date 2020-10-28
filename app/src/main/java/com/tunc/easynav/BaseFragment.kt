package com.tunc.easynav

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.easynav.src.FragmentController
import com.android.easynav.src.FragmentOption

abstract class BaseFragment : Fragment() {

    lateinit var navigator: FragmentController
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            navigator = context.navigator
        }
    }

    open fun mainNavigate(fragment: Fragment, block: FragmentOption.Builder.() -> Unit = {}) {
        navigator.mainNavigate(FragmentOption.build(fragment,block))
    }

    open fun childNavigate(fragment: Fragment, block: FragmentOption.Builder.() -> Unit = {}) {
        navigator.childNavigate(FragmentOption.build(fragment,block))
    }


/*    override fun onDestroyView() {
        val parentViewGroup = view?.parent as ViewGroup?
        parentViewGroup?.removeAllViews()
        super.onDestroyView()
    }*/

}