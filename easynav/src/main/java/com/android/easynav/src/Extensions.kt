package com.android.easynav.src

import androidx.fragment.app.Fragment

/**
 * Created by Kemal Tunç on 2020-09-30
 */


fun Fragment.getFragTag(extra: String = ""): String {
    return this::class.java.simpleName + extra
}

inline fun <reified T : BaseController> findController(
    fragment: Fragment?,
    block: FragmentOption.Builder.() -> Unit = {}
) = FragmentOption.build<T>(fragment, block)


inline fun <reified T : BaseController> navigate(
    fragment: Fragment,
    block: FragmentOption.Builder.() -> Unit = {}
) {

}

inline fun <reified T : BaseController> Navigator.navigateExt(
    fragment: Fragment,
    block: FragmentOption.Builder.() -> Unit = {}
) {
    this.navigate(findController<T>(fragment, block))
}