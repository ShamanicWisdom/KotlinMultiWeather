package com.example.szaman.kotlinmultiweather.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.szaman.kotlinmultiweather.R
import common.szaman.data.LocationData
import com.example.szaman.kotlinmultiweather.utility.DefaultValue

/**
 * Created by Szaman on 2017-06-22.
 */
class WindFragment : Fragment() {

    private var thread: Thread? = null
    private var location: LocationData? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_wind, container, false)
        location = LocationData()

        thread = createThread(rootView)
        thread!!.start()

        return rootView
    }

    private fun createThread(rootView: View): Thread {
        return object : Thread() {
            override fun run() {
                try {
                    while (!isInterrupted) {
                        activity!!.runOnUiThread { setValues(rootView) }
                        Thread.sleep(DefaultValue.defaultRefreshRatio.toLong() * 1000)
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }
    }

    override fun onStop() {
        super.onStop()
        thread!!.interrupt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun setValues(rootView: View) {
        val windForce = rootView.findViewById(R.id.windForceValue) as TextView
        val windDirection = rootView.findViewById(R.id.windDirectionValue) as TextView
        val humidity = rootView.findViewById(R.id.humidityValue) as TextView

        windForce.text = "" + DefaultValue.weather?.wind?.windForce.toString()
        windDirection.text = "" + DefaultValue.weather?.wind?.windDirection.toString()
        humidity.text = "" + DefaultValue?.weather?.wind?.humidity.toString() + "%"
    }

    companion object {


        fun newInstance(): WindFragment {
            val fragment = WindFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}