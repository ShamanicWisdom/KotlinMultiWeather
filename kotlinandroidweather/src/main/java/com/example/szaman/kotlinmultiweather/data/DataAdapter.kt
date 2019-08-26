package com.example.szaman.kotlinmultiweather.data


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.szaman.kotlinmultiweather.fragment.ForecastFragment
import com.example.szaman.kotlinmultiweather.fragment.LocationFragment
import com.example.szaman.kotlinmultiweather.fragment.WindFragment

/**
 * Created by Szaman on 2017-05-28.
 */

class DataAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return LocationFragment.newInstance()
            1 -> return WindFragment.newInstance()
            2 -> return ForecastFragment.newInstance()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Dane"
            1 -> return "Wiatr"
            2 -> return "Progn."
        }
        return null
    }
}