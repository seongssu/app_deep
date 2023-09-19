package com.android.imagesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.android.imagesearch.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val tabList = listOf("Search", "Inventory")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewPager2.adapter = ViewPager2Adapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = tabList[position]
        }.attach()

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            var currentState = 0
            var currentPosition = 0
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
               if(currentState == ViewPager2.SCROLL_STATE_DRAGGING && currentPosition ==position){
                   if(currentPosition ==0) binding.viewPager2.currentItem =1
                   else if(currentPosition ==1) binding.viewPager2.currentItem =0
               }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
            override fun onPageSelected(position: Int) {
                currentPosition = position
                super.onPageSelected(position)
            }
            override fun onPageScrollStateChanged(state: Int) {
                currentState = state
                super.onPageScrollStateChanged(state)
            }
        })
    }
}