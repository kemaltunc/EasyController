package com.tunc.easynav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.easynav.src.FragmentController
import com.android.easynav.src.animation.Animation


class MainActivity : AppCompatActivity() {


    val navigator = FragmentController(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navigator.apply {
            init(savedInstanceState)
            setAnimation(Animation.build { })
            startFragment(TabFragment.newInstance()) {
                history = false
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        navigator.saveState(outState)
        super.onSaveInstanceState(outState)
    }


}