package com.android.easynav.src

import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import com.android.easynav.src.Navigator.Companion.backCurrentFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

inline fun <reified T : BaseController> BottomNavigationView.create(
    fragments: List<Fragment>,
    controller: Navigator
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
        val findItem = bottomMenu.find { it.fragment::class.java.simpleName == fragTag }

        findItem?.let {
            this.selectedItemId = it.menuId
        }
    }
}

fun Fragment.getFragTag(extra: String = ""): String {
    return this::class.java.simpleName + extra
}