package com.tunc.easynav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.easynav.src.FragmentController
import com.android.easynav.src.findController


class MainActivity : AppCompatActivity() {


    val navigator = FragmentController(this,R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigator.startFragment(findController<MainController>(TabFragment()) {
            history = false
        })

    }

}