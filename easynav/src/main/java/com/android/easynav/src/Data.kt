package com.android.easynav.src

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


/**
 * Created by Kemal Tunç on 2020-09-30
 */


data class ControllerStack(
    var containerId: Int,
    var controllerName: String,
    var fm: FragmentManager
)

data class FragmentStack(
    var tag: String,
    var controllerName: String
)

data class BottomMenu(
    var menuId: Int,
    var fragment: Fragment
)