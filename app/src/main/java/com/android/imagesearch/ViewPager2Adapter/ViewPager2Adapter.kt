package com.android.imagesearch.ViewPager2Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.imagesearch.InventoryFragment.InventoryFrgment
import com.android.imagesearch.SearchFragment.SearchFragment

class ViewPager2Adapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int =2
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SearchFragment()
            else -> InventoryFrgment()
        }
    }

}