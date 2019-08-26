package com.example.szaman.kotlinmultiweather.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.szaman.kotlinmultiweather.R
import common.szaman.data.LocationData
import com.example.szaman.kotlinmultiweather.utility.DefaultValue
import com.example.szaman.kotlinmultiweather.utility.ImageChooser

/**
 * Created by Szaman on 2017-06-19.
 */
class LocationFragment : Fragment() {

    private var thread: Thread? = null
    private var location: LocationData? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_location, container, false)
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
        val locationName = rootView.findViewById(R.id.locationNameValue) as TextView
        val locationLatitude = rootView.findViewById(R.id.locationLatitudeValue) as TextView
        val locationLongitude = rootView.findViewById(R.id.locationLongitudeValue) as TextView
        val locationTemperature = rootView.findViewById(R.id.locationTemperatureValue) as TextView
        val locationPressure = rootView.findViewById(R.id.locationPressureValue) as TextView
        val locationDescription = rootView.findViewById(R.id.locationDescriptionValue) as TextView
        val weatherImage = rootView.findViewById(R.id.imageWeather) as ImageView

        locationName.text = "" + DefaultValue.weather?.locationData?.city.toString() + ", " + DefaultValue.weather?.locationData?.country.toString()
        locationLongitude.text = "" + DefaultValue.weather?.locationData?.longitude.toString()
        locationLatitude.text = "" + DefaultValue.weather?.locationData?.latitude.toString()
        locationTemperature.text = "" + DefaultValue.weather?.actualWeather?.temperature.toString()
        locationPressure.text = "" + DefaultValue.weather?.actualWeather?.pressure.toString()
        locationDescription.text = "" + DefaultValue.weather?.actualWeather?.description.toString()
        weatherImage.setImageResource(ImageChooser.getImage(DefaultValue.weather?.actualWeather?.imageCode.toString()))
        /*
        locationName.text = "" + DefaultValue.weather!!.locationData!!.city.toString() + ", " +
                DefaultValue.weather?.locationData?.country.toString()
        locationLongitude.text = "" + DefaultValue.weather?.locationData?.longitude.toString()
        locationLatitude.text = "" + DefaultValue.weather?.locationData?.latitude.toString()
        locationTemperature.text = "" + DefaultValue.weather?.actualWeather?.temperature.toString()
        locationPressure.text = "" + DefaultValue.weather?.actualWeather?.pressure.toString()
        locationDescription.text = "" + DefaultValue.weather?.actualWeather?.description.toString()
        //weatherImage.setImageResource(ImageChooser.getImage(R.drawable.img3200.toString()))//(DefaultValue.weather?.actualWeather?.imageCode.toString()))
        */
    }

    companion object {

        fun newInstance(): LocationFragment {
            val fragment = LocationFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}