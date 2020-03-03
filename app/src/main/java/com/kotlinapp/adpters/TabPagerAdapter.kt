package com.kotlinapp.adpters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kotlinapp.fragments.FirstFragment
import com.kotlinapp.fragments.FourthFragment
import com.kotlinapp.fragments.SecondFragment
import com.kotlinapp.fragments.ThirdFragment


class TabPagerAdapter(fm: FragmentManager, private var tabCount: Int) :
            FragmentPagerAdapter(fm) {
 
    override fun getItem(position: Int): Fragment {
 
        when (position) {
            0 -> return FirstFragment()
            1 -> return SecondFragment()
            2 -> return ThirdFragment()
            3 -> return FourthFragment()
            else -> return Fragment()
        }
    }
 
    override fun getCount(): Int {
        return tabCount
    }
}