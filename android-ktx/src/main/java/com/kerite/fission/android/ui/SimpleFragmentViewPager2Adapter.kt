package com.kerite.fission.android.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

typealias OnFragmentChangeListener = (@receiver:IdRes Int) -> Unit

/**
 * @author Kerite
 */
class SimpleFragmentViewPagerAdapter(
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle,
    private vararg val idToFragmentProducer: Pair<Int, () -> Fragment>,
    var onFragmentChangeListener: OnFragmentChangeListener = {},
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    val pageChangeCallback: ViewPager2.OnPageChangeCallback =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                onFragmentChangeListener(idToFragmentProducer[position].first)
            }
        }
    private var viewPagerGetter: () -> ViewPager2? = { null }


    constructor(
        fragmentActivity: FragmentActivity,
        vararg idToFragmentProducer: Pair<Int, () -> Fragment>,
        onFragmentChangeListener: OnFragmentChangeListener = {}
    ) : this(
        fragmentActivity.supportFragmentManager,
        fragmentActivity.lifecycle,
        onFragmentChangeListener = onFragmentChangeListener,
        idToFragmentProducer = idToFragmentProducer,
    )

    constructor(
        fragmentActivity: Fragment,
        vararg idToFragmentProducer: Pair<Int, () -> Fragment>,
        onFragmentChangeListener: OnFragmentChangeListener = {}
    ) : this(
        fragmentActivity.childFragmentManager,
        fragmentActivity.lifecycle,
        onFragmentChangeListener = onFragmentChangeListener,
        idToFragmentProducer = idToFragmentProducer,
    )

    override fun getItemCount(): Int {
        return idToFragmentProducer.size
    }

    override fun createFragment(position: Int): Fragment {
        return idToFragmentProducer[position].second()
    }

    fun getPosition(@IdRes id: Int): Int {
        for (pair in idToFragmentProducer) {
            if (pair.first == id) {
                return idToFragmentProducer.indexOf(pair)
            }
        }
        return 0
    }

    fun setupViewPager2(viewPager2: ViewPager2) {
        viewPager2.adapter = this
        viewPagerGetter = { viewPager2 }
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                viewPager2.registerOnPageChangeCallback(pageChangeCallback)
            }

            override fun onPause(owner: LifecycleOwner) {
                super.onPause(owner)
                viewPager2.unregisterOnPageChangeCallback(pageChangeCallback)
            }
        })
    }

    fun setupBottomNavigationView(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setOnItemSelectedListener {
            viewPagerGetter()?.currentItem = getPosition(it.itemId)
            true
        }
    }
}
