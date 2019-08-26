package com.szaman.example.app

import com.szaman.example.view.MainView
import common.szaman.model.Weather
import common.szaman.utility.JSONDesktopDataFetcher
import tornadofx.App
import java.text.ParseException
import com.szaman.example.utility.DefaultValue
import org.json.JSONException

class MyApp: App(MainView::class, Styles::class)

fun init()
{
    DefaultValue.weather = fetchData(DefaultValue.system + "", DefaultValue.selectedLocation.city, DefaultValue.selectedLocation.country)
    println(DefaultValue.weather?.locationData?.city)
    println("DZIALA?")
}

/*
fun start(stage: Stage)
{
    stage.isResizable = false
    DefaultValue.weather = fetchData(DefaultValue.system + "", DefaultValue.selectedLocation.city, DefaultValue.selectedLocation.country)
    println(DefaultValue.weather?.locationData?.city)
}
*/

fun fetchData(unit: String, city: String?, country: String?): Weather?
{
    var weather: Weather? = null

    try {
        val json = JSONDesktopDataFetcher.getJSON(
                DefaultValue.WEATHER_URL[0] + city +     //miasto
                        "," + country +                          //kraj
                        DefaultValue.WEATHER_URL[1] + unit +     //jednostki
                        DefaultValue.WEATHER_URL[2]
        )
        weather = JSONDesktopDataFetcher.getAllData(json)

    } catch (e: JSONException) {
        e.printStackTrace()
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    DefaultValue.weather = weather//fetchData(DefaultValue.system + "", DefaultValue.selectedLocation.city, DefaultValue.selectedLocation.country)
    println(DefaultValue.weather?.locationData?.city)

    return weather
}