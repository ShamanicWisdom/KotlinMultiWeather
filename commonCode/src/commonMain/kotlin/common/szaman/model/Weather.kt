package common.szaman.model

/**
 * Created by Szaman on 2017-06-21.
 */

data class Weather(var locationData: LocationData? = null, var actualWeather: ActualWeather? = null, var wind: Wind? = null, var forecast: List<Forecast>? = null)
/*{
    constructor() : this(locationData, actualWeather, wind, forecast)
}*/