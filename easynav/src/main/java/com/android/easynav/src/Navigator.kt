package com.android.easynav.src

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


/**
 * Created by Kemal Tunç on 2020-09-30
 */

interface Navigator {

    fun startFragment(fragmentOption: FragmentOption)

    fun addControllerAndStart(
        containerId: Int,
        fragmentManager: FragmentManager,
        fragmentOption: FragmentOption
    )

    fun addController(containerId: Int, controllerName: String, fragmentManager: FragmentManager)

    fun navigate(fragmentOption: FragmentOption)

    fun navigateUp()

    fun navigateUp(code: Int, intent: Intent)

    fun getFragmentByTag(tag: String?, fm: FragmentManager?): Fragment?


    fun removeFragment(fragmentManager: FragmentManager, fragment: Fragment?, index: Int)

    fun removeHistory(fragmentManager: FragmentManager)

    fun getFragmentManager(name: String): FragmentManager?

    fun currentFragment(): Fragment?
}