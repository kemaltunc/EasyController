package com.android.easynav.src

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.easynav.controller.data.FragmentStack
import com.android.easynav.src.animation.Animation
import com.google.android.material.bottomnavigation.BottomNavigationView


/**
 * Created by Kemal Tunç on 2020-09-30
 */

interface Navigator {

    fun init(bundle: Bundle?)

    fun createChildContainer(containerId: Int)

    fun startFragment(fragment: Fragment, block: FragmentOption.Builder.() -> Unit = {})

    fun mainNavigate(fragmentOption: FragmentOption)

    fun childNavigate(fragmentOption: FragmentOption)

    fun findController(controllerName: String, fragmentOption: FragmentOption)

    fun navigateUp()

    fun navigateUp(code: Int, intent: Intent)

    fun saveState(outState: Bundle)

    fun saveBottomMenuState(outState: Bundle)

    fun createdNavMenu(outState: Bundle?): Boolean

    fun currentFragment(fragment: (fragment: Fragment) -> Unit)

    fun createBottomMenu(
        controllerName: String,
        view: BottomNavigationView,
        fragments: List<Fragment>
    )

    fun backCurrentFragment(f: (tag: String, stack: FragmentStack) -> Unit)

    fun setAnimation(animation: Animation)

    fun getFragmentState(): Bundle

}