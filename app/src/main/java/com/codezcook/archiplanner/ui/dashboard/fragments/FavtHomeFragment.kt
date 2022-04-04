package com.codezcook.archiplanner.ui.dashboard.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.codezcook.archiplanner.adapter.ViewPagerAdapter
import com.codezcook.archiplanner2.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_favthome.*


class FavtHomeFragment  : Fragment(R.layout.fragment_favthome) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.favts)

        setUI(view)
    }

    private fun setUI(view: View) {


        val adapter = ViewPagerAdapter(this)
        view_pager.adapter = adapter


        TabLayoutMediator(tab_layout, view_pager ) { tab, position ->
            tab.text = if (position == 0) "Plans" else "Elevation"
        }.attach()


    }

}