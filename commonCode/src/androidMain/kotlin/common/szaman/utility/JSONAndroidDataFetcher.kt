package common.szaman.utility

import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.ParseException
import java.util.*
import common.szaman.model.*
import org.json.JSONArray
import java.io.BufferedInputStream
import java.net.MalformedURLException

/**
 * Created by Szaman on 2017-06-21.
 */

object JSONAndroidDataFetcher {

    @Throws(JSONException::class, ParseException::class)
    fun getAllData(json: JSONObject?): Weather {

        return Weather(
            getLocationData(json),
            getActualWeatherData(json),
            getWindData(json),
            getForecastData(json)
        )
    }

    @Throws(JSONException::class, ParseException::class)
    fun getActualWeatherData(jsonObj: JSONObject?): ActualWeather {

        val json = jsonObj
        val temperature = json?.getJSONArray("list")?.getJSONObject(0)?.getJSONObject("main")?.getString("temp")
        val pressure = json?.getJSONArray("list")?.getJSONObject(0)?.getJSONObject("main")?.getString("pressure")
        val description = json?.getJSONArray("list")?.getJSONObject(0)?.getJSONArray("weather")?.getJSONObject(0)?.getString("description")
        val imageCode = json?.getJSONArray("list")?.getJSONObject(0)?.getJSONArray("weather")?.getJSONObject(0)?.getString("id")
        return ActualWeather(temperature, pressure, description, imageCode)
    }

    @Throws(JSONException::class, ParseException::class)
    fun getWindData(jsonObj: JSONObject?): Wind {

        val json = jsonObj
        val windForce = json?.getJSONArray("list")?.getJSONObject(0)?.getJSONObject("wind")?.getString("speed")
        val windDirection = json?.getJSONArray("list")?.getJSONObject(0)?.getJSONObject("wind")?.getString("deg")
        val humidity = json?.getJSONArray("list")?.getJSONObject(0)?.getJSONObject("main")?.getString("humidity")

        return Wind(windForce, windDirection, humidity)
    }

    @Throws(JSONException::class, ParseException::class)
    fun getForecastData(jsonObj: JSONObject?): List<Forecast> {

        val furtherWeathers = ArrayList<Forecast>(7)

        var indexer = 1
        for (index in 1..5) {
            val row = jsonObj?.getJSONArray("list")?.getJSONObject(indexer)
            val minTemperature = row?.getJSONObject("main")?.getString("temp_min")
            val maxTemperature = row?.getJSONObject("main")?.getString("temp_max")
            val day = row?.getString("dt_txt")
            val date = day?.substring(0, 10)
            val imageCode = row?.getJSONArray("weather")?.getJSONObject(0)?.getString("id")

            indexer += 8
            furtherWeathers.add(Forecast(minTemperature, maxTemperature, date, imageCode))
        }

        return furtherWeathers
    }

    @Throws(JSONException::class, ParseException::class)
    fun getLocationData(jsonObj: JSONObject?): LocationData {

        val json = jsonObj
        val country = json?.getJSONObject("city")?.getString("country")
        val city = json?.getJSONObject("city")?.getString("name")
        val longitude = json?.getJSONObject("city")?.getJSONObject("coord")?.getString("lon")
        val latitude = json?.getJSONObject("city")?.getJSONObject("coord")?.getString("lat")

        return LocationData(country, city, longitude, latitude)
    }

    private fun connect(jsonURL: String): Any {
        try {
            val url = URL(jsonURL)
            val con = url.openConnection() as HttpURLConnection

            //CON PROPS
            con.requestMethod = "GET"
            con.doInput = true

            return con

        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return "URL ERROR " + e.message

        } catch (e: IOException) {
            e.printStackTrace()
            return "CONNECT ERROR " + e.message
        }
    }

    fun getJSON(jsonURL: String): JSONObject?
    {
        val connection = connect(jsonURL)
        if (connection.toString().startsWith("Error")) {
            return null
        }
        //DOWNLOAD
        try {
            val con = connection as HttpURLConnection
            //if response is HTTP OK
            if (con.responseCode == 200) {
                //GET INPUT FROM STREAM
                val `is` = BufferedInputStream(con.inputStream)
                val br = BufferedReader(InputStreamReader(`is`))

                val content = StringBuffer()
                var line: String?

                do {
                    line = br.readLine()

                    if (line == null){ break}

                    content.append(line + "\n");

                } while (true)

                //CLOSE RESOURCES
                br.close()
                `is`.close()

                //RETURN JSON
                return JSONObject(content.toString())

            } else {
                return null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}