package com.android.easynav.src.custom

import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import com.android.easynav.controller.data.BottomMenu
import com.android.easynav.src.FragmentController
import com.android.easynav.src.FragmentOption
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Created by Kemal Tunç on 2020-09-30
 */


fun BottomNavigationView.create(
    controllerName: String,
    fragments: List<Fragment>,
    navigator: FragmentController,
    createdNavMenu: Boolean
) {

    var changeScreen = true

    val bottomMenu = ArrayList<BottomMenu>()

    this.menu.forEachIndexed { index, item ->
        bottomMenu.add(
            BottomMenu(
                item.itemId,
                fragments[index]
            )
        )
    }

    if (!createdNavMenu) {
        navigator.findController(
            controllerName,
            FragmentOption.build(
                bottomMenu.first().fragment
            ) {
                tabMenuFragment = true
                groupId = bottomMenu.first().menuId
                firstTabFragment =true
            })
    }

    this.setOnNavigationItemSelectedListener { menuItem ->
        val item = bottomMenu.find { it.menuId == menuItem.itemId }
        if (item != null && changeScreen) {
            navigator.findController(controllerName,
                FragmentOption.build(item.fragment) {
                    tabMenuFragment = true
                    groupId = item.menuId
                })
        }
        changeScreen = true
        return@setOnNavigationItemSelectedListener true
    }

    navigator.backCurrentFragment { fragTag, stack ->
        changeScreen = false
        if (stack.groupId != 0) this.selectedItemId = stack.groupId
    }


}

