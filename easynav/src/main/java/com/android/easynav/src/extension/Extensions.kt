package com.android.easynav.src.extension

import androidx.fragment.app.Fragment

/**
 * Created by Kemal Tunç on 2020-09-30
 */


fun Fragment.getFragTag(extra: String = ""): String {
    return this::class.java.simpleName + extra
}
