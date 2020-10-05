package com.tunc.easynav.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.easynav.src.Navigator
import com.tunc.easynav.ChildController
import com.tunc.easynav.R

class FourthFragment : Fragment() {

    lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_fourth, container, false)
        

        return root
    }

}