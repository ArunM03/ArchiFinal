package com.codezcook.archiplanner.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codezcook.archiplanner.ui.dashboard.elevation.ElevationImageFragment
import com.codezcook.archiplanner.ui.dashboard.fragments.PlanViewFragment
import kotlin.reflect.typeOf

class ElevationFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val count : Int) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
       return    ElevationImageFragment(position)
    }

    override fun getItemCount(): Int {
        return count
    }
}