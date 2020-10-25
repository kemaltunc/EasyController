package com.android.easynav.src

import android.app.Activity
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Created by Kemal Tunç on 2020-09-30
 */


class FragmentController(private val activity: Activity, private val containerId: Int) : Navigator {

    private val controllerStack = ArrayList<ControllerStack>()
    val fragmentStack = ArrayList<FragmentStack>()

    var backFragment: (tag: String) -> Unit = { _ -> }

    override fun startFragment(fragmentOption: FragmentOption) {
        val appCompatActivity = (activity as AppCompatActivity)

        addController(
            containerId,
            fragmentOption.controllerName,
            appCompatActivity.supportFragmentManager
        )
        appCompatActivity.onBackPressedDispatcher.addCallback(appCompatActivity, onBackListener)

        navigate(fragmentOption)
    }

    override fun addControllerAndStart(
        containerId: Int,
        fragmentManager: FragmentManager,
        fragmentOption: FragmentOption
    ) {
        addController(containerId, fragmentOption.controllerName, fragmentManager)
        navigate(fragmentOption)
    }

    override fun addController(
        containerId: Int,
        controllerName: String,
        fragmentManager: FragmentManager
    ) {
        val index = controllerStack.indexOfFirst { it.controllerName == controllerName }
        if (index != -1) {
            controllerStack.removeAt(index)
        }
        controllerStack.add(
            ControllerStack(containerId, controllerName, fragmentManager)
        )
    }

    override fun navigate(fragmentOption: FragmentOption) {

        val findNav = controllerStack.find { it.controllerName == fragmentOption.controllerName }

        if (findNav != null) {
            val fragName = fragmentOption.fragment?.getFragTag(fragmentOption.label) ?: ""

            val index = fragmentStack.indexOfFirst { it.tag == fragName }

            if (index != -1 && !fragmentOption.clearHistory) {
                val findFrag = getFragmentByTag(fragName, findNav.fm)
                removeFragment(findNav.fm, findFrag, index)
            }

            if (fragmentOption.clearHistory) removeHistory(findNav.fm)

            val transaction = findNav.fm.beginTransaction()
                .replace(findNav.containerId, fragmentOption.fragment!!, fragName)

            if (fragmentOption.history) {
                transaction.addToBackStack(fragName)
                fragmentStack.add(FragmentStack(fragName, findNav.controllerName))
            }

            transaction.commit()

        } else {
            throw NullPointerException("Not found controller")
        }

    }

    override fun navigateUp() {
        activity.onBackPressed()
    }

    override fun navigateUp(code: Int, intent: Intent) {
        currentFragment()?.let {
            it.targetFragment!!.onActivityResult(code, Activity.RESULT_OK, intent)
        }
        navigateUp()
    }

    override fun currentFragment(): Fragment? {
        val frag = fragmentStack.last()
        return getFragmentByTag(frag.tag, getFragmentManager(frag.controllerName))
    }


    override fun getFragmentByTag(tag: String?, fm: FragmentManager?): Fragment? {
        return fm?.findFragmentByTag(tag)
    }

    override fun removeFragment(fragmentManager: FragmentManager, fragment: Fragment?, index: Int) {
        fragmentStack.removeAt(index)
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit()
        }
    }

    override fun removeHistory(fragmentManager: FragmentManager) {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        fragmentStack.clear()
    }

    override fun getFragmentManager(name: String): FragmentManager? {
        return controllerStack.find { it.controllerName == name }?.fm
    }

    private val onBackListener = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (fragmentStack.size > 1) {
                val findNav = fragmentStack.takeLast(2).first()
                val currentNav = getFragmentManager(fragmentStack.last().controllerName)

                val fm = getFragmentManager(fragmentStack.takeLast(2).first().controllerName)

                if (fm == currentNav) {
                    fm?.popBackStack(findNav.tag, 0)
                    backFragment(findNav.tag)
                } else {
                    currentNav?.popBackStack()
                }
                fragmentStack.remove(fragmentStack.last())
            } else {
                activity.finish()
            }

        }
    }

    fun backCurrentFragment(f: (tag: String) -> Unit) {
        backFragment = f
    }

    inline fun <reified T : BaseController> createBottomMenu(
        view: BottomNavigationView,
        fragments: List<Fragment>
    ) {
        view.create<T>(
            fragments,
            this
        )
    }


}

