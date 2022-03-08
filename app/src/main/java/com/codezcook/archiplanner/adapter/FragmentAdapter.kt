package com.codezcook.archiplanner.adapter

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.findFragment
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.codezcook.archiplanner.ui.dashboard.elevation.ElevationImageFragment
import com.codezcook.archiplanner.ui.dashboard.fragments.PlanViewFragment
import kotlinx.android.synthetic.main.fragment_color.*
import kotlin.reflect.typeOf

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val count : Int) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
         return  PlanViewFragment(position)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.itemView.apply {
            super.onBindViewHolder(holder, position, payloads)
                setOnClickListener {
                    Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show()
                }
          this.rootView.setOnTouchListener { view, event ->
                var result = true
                if (event.pointerCount >= 2 || view.canScrollHorizontally(1) && canScrollHorizontally(
                        -1
                    )
                ) {
                    //multi-touch event
                    result = when (event.action) {
                        MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                            // Disallow RecyclerView to intercept touch events.
                            parent.requestDisallowInterceptTouchEvent(true)
                            // Disable touch on view
                            false
                        }
                        MotionEvent.ACTION_UP -> {
                            // Allow RecyclerView to intercept touch events.
                            parent.requestDisallowInterceptTouchEvent(false)
                            true
                        }
                        else -> true
                    }
                }
                result
            }
        }
    }

    override fun getItemCount(): Int {
        return count
    }
}