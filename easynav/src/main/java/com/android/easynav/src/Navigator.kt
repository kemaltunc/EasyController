package com.android.easynav.src

import android.app.Activity
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.android.easynav.src.NavigatorData.Companion.backFragment
import com.android.easynav.src.NavigatorData.Companion.fragStackList
import com.android.easynav.src.NavigatorData.Companion.navigatorList
import com.google.android.material.bottomnavigation.BottomNavigationView


/**
 * Created by Kemal Tunç on 2020-09-30
 */


class Navigator(
    private val controllerName: String,
    private var fragment: Fragment?,
    private val activity: Activity?,
    private val label: String,
    private val clearHistory: Boolean,
    private val history: Boolean,
    private val popupFragment: Fragment?
) {


    constructor(builder: Builder) : this(
        builder.controllerName,
        builder.fragment,
        builder.activity,
        builder.label,
        builder.clearHistory,
        builder.history,
        builder.popupFragment
    )

    private val getActivity: Activity?
        get() {
            val last = fragStackList.last()
            val currentFragment = getFragmentByTag(last.tag, last.fm)
            return currentFragment?.activity
        }

    fun bind(containerId: Int) {
        if (activity != null) {

            val appCompatActivity = (activity as AppCompatActivity)

            addController(containerId, appCompatActivity.supportFragmentManager)

            navigate()

            appCompatActivity.onBackPressedDispatcher.addCallback(appCompatActivity, onBackListener)

        }
    }

    fun addController(containerId: Int, fragmentManager: FragmentManager) {

        val index = navigatorList.indexOfFirst { it.name == controllerName }
        if (index != -1) {
            navigatorList.removeAt(index)
        }
        navigatorList.add(
            ControllerDetail(containerId, controllerName, fragmentManager)
        )
    }

    fun currentFragment(): Fragment? {
        val frag = fragStackList.last()

        return getFragmentByTag(frag.tag, frag.fm)
    }

    fun navigate() {
        val findNav = navigatorList.find { it.name == controllerName }

        if (findNav != null && fragment != null) {
            val fragName = fragment?.getFragTag(label) ?: ""

            val index = fragStackList.indexOfFirst { it.tag == fragName }

            if (index != -1 && !clearHistory) {
                val findFrag = getFragmentByTag(fragName, findNav.fm)
                removeFragment(findNav.fm, findFrag, index)
            }

            if (clearHistory) removeHistory(findNav.fm)

            val transaction = findNav.fm.beginTransaction()
                .replace(findNav.containerId, fragment!!, fragName)

            if (history) {
                transaction.addToBackStack(fragName)
                fragStackList.add(Stack(fragName, findNav.fm))
            }

            transaction.commit()

        } else {
            throw NullPointerException("Not found controller")
        }
    }

    private fun getFragmentByTag(tag: String, fm: FragmentManager): Fragment? {
        return fm.findFragmentByTag(tag)
    }


    private fun removeFragment(fragmentManager: FragmentManager, fragment: Fragment?, index: Int) {
        fragStackList.removeAt(index)
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit()
        }
    }

    private fun removeHistory(fragmentManager: FragmentManager) {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        fragStackList.clear()
    }

    fun navigateUp() {
        if (popupFragment != null) {
            val fragTag = fragment?.getFragTag(label)

            val frag = fragStackList.find { it.tag == fragTag }


        }
        getActivity?.onBackPressed()
    }

    inline fun <reified T : BaseController> createBottom(view: BottomNavigationView, fragments: List<Fragment>) {
       view.create<T>(
           fragments
        )
    }

    private val onBackListener = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (fragStackList.size > 1) {

                val findNav = fragStackList.takeLast(2).first()
                val currentNav = fragStackList.last().fm

                if (findNav.fm == currentNav) {
                    findNav.fm.popBackStack(findNav.tag, 0)
                    backFragment(findNav.tag)
                } else {
                    currentNav.popBackStack()
                }
                fragStackList.remove(fragStackList.last())

            } else {
                getActivity?.finish()
            }

        }
    }

    companion object {

        inline fun <reified T : BaseController> bind(
            activity: Activity,
            containerId: Int,
            startFragment: Fragment,
            block: Builder.() -> Unit = {}
        ) = Builder(T::class.java.simpleName, startFragment, activity).apply(block)
            .build().bind(containerId)

        inline fun <reified T : BaseController> find(
            fragment: Fragment,
            block: Builder.() -> Unit = {}
        ) = Builder(T::class.java.simpleName, fragment).apply(block).build()

        inline fun navigateUp(
            block: Builder.() -> Unit = {}
        ) = Builder("", null).apply(block).build().navigateUp()

        fun backCurrentFragment(f: (tag: String) -> Unit) {
            backFragment = f
        }
    }

    class Builder(
        val controllerName: String,
        val fragment: Fragment?,
        val activity: Activity? = null
    ) {

        var label: String = ""
        var clearHistory: Boolean = false
        var history: Boolean = true
        var popupFragment: Fragment? = null


        fun build() = Navigator(this)
    }

}