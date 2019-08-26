package common.szaman.utility

import common.szaman.model.*
import java.util.*

/**
 * Created by Szaman on 2017-05-27.
 */
object DefaultValue {

    //val location = Location("lodz","pl")

    var defaultRefreshRatio = 10
    var defaultLongitude: Double = 19.456
    var defaultLatitude: Double = 51.747
    var defaultLocation = Location("Lodz", "PL")    //

    var selectedLocation = Location("Lodz", "PL")
    var system = "metric"
    var celsiusSymbol = "°C"
    var fahrenheitSymbol = "°F"

    var defaultDataRefreshRatio: Long = 10 //minuty

    var weather: Weather? = null

    var locations = KnownLocations()

    val WEATHER_URL = arrayOf(
        "https://api.openweathermap.org/data/2.5/forecast?q=",
        "&units=",
        "&appid=d455dbaae9911fbb9886ecfe63d7e870"
    )
}