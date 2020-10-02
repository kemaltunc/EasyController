package com.tunc.easynav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.easynav.src.Navigator


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Navigator.bind<MainController>(this, R.id.container, TabFragment()) {
            history = false
        }
    }

}