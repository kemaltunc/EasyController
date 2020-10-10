package com.android.easynav.src

import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import com.android.easynav.src.Navigator.Companion.backCurrentFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Created by Kemal Tunç on 2020-09-30
 */


inline fun <reified T : BaseController> BottomNavigationView.create(
    fragments: List<Fragment>
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

        item?.let {
            if (changeScreen) Navigator.find<T>(item.fragment).navigate()
        }
        changeScreen = true
        return@setOnNavigationItemSelectedListener true
    }


    backCurrentFragment { fragTag ->
        changeScreen = false
        val menu = bottomMenu.find { it.fragment.getFragTag("") == fragTag }

        menu?.let {
            this.selectedItemId = it.menuId
        } ?: let {
            val index = NavigatorData.fragStackList.indexOfFirst { it.tag == fragTag }

            for (i in index downTo 0) {
                bottomMenu.find { it.fragment.tag == NavigatorData.fragStackList[i].tag }?.let {
                    this.selectedItemId = it.menuId
                    return@backCurrentFragment
                }
            }
        }
    }
}

fun Fragment.getFragTag(extra: String = ""): String {
    return this::class.java.simpleName + extra
}