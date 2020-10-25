package com.tunc.easynav

import android.content.Context
import androidx.fragment.app.Fragment
import com.android.easynav.src.FragmentController

abstract class BaseFragment : Fragment() {

    lateinit var navigator: FragmentController
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) {
            navigator = context.navigator
        }
    }

}