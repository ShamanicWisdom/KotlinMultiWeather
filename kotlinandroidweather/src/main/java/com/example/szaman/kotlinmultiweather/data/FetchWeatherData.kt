package com.example.szaman.kotlinmultiweather.data

import android.os.AsyncTask
import org.json.JSONException
import java.text.ParseException

import common.szaman.model.Weather
import com.example.szaman.kotlinmultiweather.utility.DefaultValue
import common.szaman.utility.JSONAndroidDataFetcher

/**
 * Created by Szaman on 2017-06-21.
 */

class FetchWeatherData : AsyncTask<String, Void, Weather>() {
    override fun doInBackground(vararg params: String): Weather? {
        var weather: Weather? = null
        val unit = params[0]
        val city = params[1]
        val country = params[2]

        try {
            val json = JSONAndroidDataFetcher.getJSON(
                DefaultValue.WEATHER_URL[0] + city +         //miasto
                        "," + country +                          //kraj
                        DefaultValue.WEATHER_URL[1] + unit +     //jednostki
                        DefaultValue.WEATHER_URL[2]
            )
            weather = JSONAndroidDataFetcher.getAllData(json)

        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return weather
    }

}
