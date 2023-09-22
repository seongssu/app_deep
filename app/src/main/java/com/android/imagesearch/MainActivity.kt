package com.android.imagesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.android.imagesearch.ViewPager2Adapter.ViewPager2Adapter
import com.android.imagesearch.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

//ViewPager2, Tablayout을 구현
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val tabList = listOf("Search", "Inventory")     //텝레이아웃에 표시되는 text
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewPager2.adapter = ViewPager2Adapter(this)

        //tablayout, viewpager2 구현 (0페이지 -> 1페이지)
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = tabList[position]
        }.attach()

        //(1<-0, 1->0 페이지) : 사실 텝이 2개라 구현할 필요는 없는데 연습했습니다.
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