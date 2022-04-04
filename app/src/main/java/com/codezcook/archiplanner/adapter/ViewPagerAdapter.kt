package com.codezcook.archiplanner.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codezcook.archiplanner.ui.dashboard.fragments.FavouritesElevationFragment
import com.codezcook.archiplanner.ui.dashboard.fragments.FavouritesFragment


class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) FavouritesFragment() else FavouritesElevationFragment()
    }

}