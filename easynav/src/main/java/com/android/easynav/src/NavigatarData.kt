package com.android.easynav.src

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class ControllerDetail(
    var containerId: Int,
    var name: String,
    var fm: FragmentManager
)

class Stack(
    var tag: String,
    var fm: FragmentManager
)

class BottomMenu(
    var menuId: Int,
    var fragment: Fragment

)

class NavigatorData {
    companion object {
        var fragStackList = ArrayList<Stack>()
        var navigatorList = ArrayList<ControllerDetail>()
        var backFragment: (tag: String) -> Unit = { _ -> }
    }
}