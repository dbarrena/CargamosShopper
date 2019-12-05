package com.cargamos.cargamosshopper.adapter.picking

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cargamos.cargamosshopper.views.main.picking.picking.PickingFragment
import com.cargamos.cargamosshopper.views.main.picking.pool.PickingPoolFragment

class PickingParentMainPagerAdapter (fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var registeredFragments = SparseArray<Fragment>()

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PickingFragment()
            else -> PickingPoolFragment()
        }
    }

    fun getRegisteredFragment(position: Int): Fragment {
        return registeredFragments.get(position)
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Pick"
            else -> {
                return "Pool"
            }
        }
    }
}