package com.tunc.easynav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.easynav.src.findController
import com.tunc.easynav.fragments.*
import kotlinx.android.synthetic.main.fragment_tab.view.*

class TabFragment : BaseFragment() {

    lateinit var root: View


    private val firstFragment by lazy { FirstFragment() }
    private val secondFragment by lazy { SecondFragment() }
    private val thirdFragment by lazy { ThirdFragment() }
    private val fourthFragment by lazy { FourthFragment() }
    private val fiveFragment by lazy { FiveFragment() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (!this::root.isInitialized) {

            root = inflater.inflate(R.layout.fragment_tab, container, false)

            navigator.addControllerAndStart(
                R.id.inner_frameview, childFragmentManager,
                findController<ChildController>(firstFragment)
            )

            navigator.createBottomMenu<ChildController>(
                root.fragment_main_navigation,
                listOf(
                    firstFragment, secondFragment, thirdFragment, fourthFragment, fiveFragment
                )
            )


        }


        return root

    }


}
