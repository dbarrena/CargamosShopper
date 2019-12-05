package com.cargamos.cargamosshopper.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cargamos.cargamosshopper.views.main.packing.PackingFragment
import com.cargamos.cargamosshopper.views.main.picking.PickingParentFragment

class MainFragmentPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragments = arrayListOf<Fragment>(
        PackingFragment(),
        PickingParentFragment()
    )
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PickingParentFragment()
            else -> PackingFragment()
        }
    }

    override fun getCount(): Int {
        return fragments.size
    }

}