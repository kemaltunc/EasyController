package com.tunc.easynav.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tunc.easynav.BaseFragment
import com.tunc.easynav.R
import kotlinx.android.synthetic.main.fragment_third.view.*

class ThirdFragment : BaseFragment() {

    lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_third, container, false)

        root.go_button.setOnClickListener {
            mainNavigate(DetailFragment())
        }

        return root
    }

}