package com.android.easynav.src

import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Created by Kemal Tunç on 2020-09-30
 */


inline fun <reified T : BaseController> BottomNavigationView.create(
    fragments: List<Fragment>,
    navigator: FragmentController
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


    this.setOnNavigationItemSelectedListener { menuItem ->

        val item = bottomMenu.find { it.menuId == menuItem.itemId }

        if (item != null && changeScreen) {
            navigator.navigate(findController<T>(item.fragment))
        }

        changeScreen = true
        return@setOnNavigationItemSelectedListener true
    }

    navigator.backCurrentFragment { fragTag ->
        changeScreen = false
        val menu = bottomMenu.find { it.fragment.getFragTag("") == fragTag }

        menu?.let {
            this.selectedItemId = it.menuId
        } ?: let {
            val index = navigator.fragmentStack.indexOfFirst { it.tag == fragTag }

            for (i in index downTo 0) {
                bottomMenu.find { it.fragment.tag == navigator.fragmentStack[i].tag }?.let {
                    this.selectedItemId = it.menuId
                    return@backCurrentFragment
                }
            }
        }
    }


}