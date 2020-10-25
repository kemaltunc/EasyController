package com.android.easynav.src

import androidx.fragment.app.Fragment

/**
 * Created by Kemal Tunç on 2020-09-30
 */


class FragmentOption(
    val controllerName: String,
    val fragment: Fragment?,
    val label: String,
    val clearHistory: Boolean,
    val history: Boolean,
    val popupFragment: Fragment?
) {

    private constructor(builder: Builder) : this(
        builder.controllerName, builder.fragment, builder.label,
        builder.clearHistory,
        builder.history,
        builder.popupFragment
    )

    companion object {
        inline fun <reified T : BaseController> build(
            fragment: Fragment?,
            block: Builder.() -> Unit
        ) = Builder(T::class.java.simpleName, fragment).apply(block).build()

    }

    class Builder(val controllerName: String, val fragment: Fragment?) {

        var label: String = ""
        var clearHistory: Boolean = false
        var history: Boolean = true
        var popupFragment: Fragment? = null

        fun build() = FragmentOption(this)
    }
}