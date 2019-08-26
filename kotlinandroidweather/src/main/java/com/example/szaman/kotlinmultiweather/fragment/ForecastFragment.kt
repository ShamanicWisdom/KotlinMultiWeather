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
import kotlinx.android.synthetic.main.fragment_forecast.*

/**
 * Created by Szaman on 2017-06-23.
 */
class ForecastFragment : Fragment() {

    private var thread: Thread? = null
    private var location: LocationData? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_forecast, container, false)
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

        var temperatureSymbol: String? = null
        if(DefaultValue.system.equals("metric"))
        {
            temperatureSymbol = DefaultValue.celsiusSymbol
        }
        else
        {
            temperatureSymbol = DefaultValue.fahrenheitSymbol
        }

        val day1Name = rootView.findViewById(R.id.day1) as TextView
        val day1Temperature = rootView.findViewById(R.id.day1Temperature) as TextView
        val day1Image = rootView.findViewById(R.id.day1Image) as ImageView

        val day2Name = rootView.findViewById(R.id.day2) as TextView
        val day2Temperature = rootView.findViewById(R.id.day2Temperature) as TextView
        val day2Image = rootView.findViewById(R.id.day2Image) as ImageView

        val day3Name = rootView.findViewById(R.id.day3) as TextView
        val day3Temperature = rootView.findViewById(R.id.day3Temperature) as TextView
        val day3Image = rootView.findViewById(R.id.day3Image) as ImageView

        val day4Name = rootView.findViewById(R.id.day4) as TextView
        val day4Temperature = rootView.findViewById(R.id.day4Temperature) as TextView
        val day4Image = rootView.findViewById(R.id.day4Image) as ImageView

        val day5Name = rootView.findViewById(R.id.day5) as TextView
        val day5Temperature = rootView.findViewById(R.id.day5Temperature) as TextView
        val day5Image = rootView.findViewById(R.id.day5Image) as ImageView

        day1Name.text = "" + DefaultValue.weather?.forecast?.get(0)?.day.toString()
        day1Temperature.text =
                ("" + DefaultValue.weather?.forecast?.get(0)?.minimalTemperature.toString() + temperatureSymbol + " - "
                        + DefaultValue.weather?.forecast?.get(0)?.maximalTemperature.toString() + temperatureSymbol)
        day1Image.setImageResource(ImageChooser.getImage(DefaultValue.weather?.forecast?.get(0)?.imageCode.toString()))

        day2Name.text = "" + DefaultValue.weather?.forecast?.get(1)?.day.toString()
        day2Temperature.text =
                ("" + DefaultValue.weather?.forecast?.get(1)?.minimalTemperature.toString() + temperatureSymbol + " - "
                        + DefaultValue.weather?.forecast?.get(1)?.maximalTemperature.toString() + temperatureSymbol)
        day2Image.setImageResource(ImageChooser.getImage(DefaultValue.weather?.forecast?.get(1)?.imageCode.toString()))

        day3Name.text = "" + DefaultValue.weather?.forecast?.get(2)?.day.toString()
        day3Temperature.text =
                ("" + DefaultValue.weather?.forecast?.get(2)?.minimalTemperature.toString() + temperatureSymbol + " - "
                        + DefaultValue.weather?.forecast?.get(2)?.maximalTemperature.toString() + temperatureSymbol)
        day3Image.setImageResource(ImageChooser.getImage(DefaultValue.weather?.forecast?.get(2)?.imageCode.toString()))

        day4Name.text = "" + DefaultValue.weather?.forecast?.get(3)?.day.toString()
        day4Temperature.text =
                ("" + DefaultValue.weather?.forecast?.get(3)?.minimalTemperature.toString() + temperatureSymbol + " - "
                        + DefaultValue.weather?.forecast?.get(3)?.maximalTemperature.toString() + temperatureSymbol)
        day4Image.setImageResource(ImageChooser.getImage(DefaultValue.weather?.forecast?.get(3)?.imageCode.toString()))

        day5Name.text = "" + DefaultValue.weather?.forecast?.get(4)?.day.toString()
        day5Temperature.text =
                ("" + DefaultValue.weather?.forecast?.get(4)?.minimalTemperature.toString() + temperatureSymbol + " - "
                        + DefaultValue.weather?.forecast?.get(4)?.maximalTemperature.toString() + temperatureSymbol)
        day5Image.setImageResource(ImageChooser.getImage(DefaultValue.weather?.forecast?.get(4)?.imageCode.toString()))
    }

    companion object {

        fun newInstance(): ForecastFragment {
            val fragment = ForecastFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}