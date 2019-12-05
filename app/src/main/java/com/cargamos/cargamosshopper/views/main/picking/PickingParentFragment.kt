package com.cargamos.cargamosshopper.views.main.picking


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager

import com.cargamos.cargamosshopper.R
import com.cargamos.cargamosshopper.adapter.picking.PickingParentMainPagerAdapter
import com.cargamos.cargamosshopper.views.main.MainActivity
import com.cargamos.cargamosshopper.views.main.picking.picking.PickingFragment
import com.cargamos.cargamosshopper.views.main.picking.pool.PickingPoolFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_picking_parent.*

class PickingParentFragment : Fragment() {
    lateinit var activity: MainActivity

    var pickingFragment: PickingFragment? = null
    var pickingPoolFragment: PickingPoolFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picking_parent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val fragmentAdapter = PickingParentMainPagerAdapter(childFragmentManager)
        contentViewPager.adapter = fragmentAdapter
        contentViewPager.setSwipeable(false)
        contentViewPager.offscreenPageLimit = 4

        tabsPicking.setupWithViewPager(contentViewPager)
        tabsPicking.tabGravity = TabLayout.GRAVITY_FILL
        tabsPicking.tabMode = TabLayout.MODE_FIXED

        fragmentAdapter.startUpdate(contentViewPager)
        pickingFragment = fragmentAdapter.instantiateItem(contentViewPager, 0) as PickingFragment
        pickingPoolFragment = fragmentAdapter.instantiateItem(contentViewPager, 1) as PickingPoolFragment
        fragmentAdapter.finishUpdate(contentViewPager)

        contentViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

                when (position) {
                    0 -> {

                    }

                    1 -> {

                    }
                }


            }

        })
    }

    fun setPickingCount(count: Int) {
        tabsPicking.getTabAt(0)?.text = "Pick ($count)"
    }

}
