package com.example.szaman.kotlinmultiweather.activity


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import android.widget.TextView
import android.widget.Toast
import com.example.szaman.kotlinmultiweather.R
import com.example.szaman.kotlinmultiweather.data.DataAdapter
import com.example.szaman.kotlinmultiweather.fragment.ForecastFragment
import com.example.szaman.kotlinmultiweather.fragment.LocationFragment
import com.example.szaman.kotlinmultiweather.fragment.WindFragment
import com.example.szaman.kotlinmultiweather.utility.DefaultValue
import common.szaman.model.*
import com.example.szaman.kotlinmultiweather.data.FetchWeatherData
import java.io.FileInputStream
import java.io.*
import java.text.DateFormat
import java.util.*
import java.util.concurrent.ExecutionException

/**
 * Created by Szaman on 2017-05-27.
 */
class MainActivity : AppCompatActivity() {
    private var timeThread: Thread? = null
    private var refreshDataThread: Thread? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initiallize()
        DefaultValue.locations = loadLocations()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveLocations(DefaultValue.locations)
        saveWeather(DefaultValue.weather)
    }


    private fun initiallize() {
        timeThread = timeThreadCreation()
        timeThread!!.start()
        refreshDataThread = refreshDataThread()
        refreshDataThread!!.start()

        val viewPager = findViewById(R.id.container) as ViewPager
        val tabLayout = findViewById(R.id.tabs) as TabLayout

        if (viewPager != null) {
            val dataAdapter = DataAdapter(supportFragmentManager)
            viewPager.adapter = dataAdapter
            if (tabLayout != null) {
                tabLayout!!.setupWithViewPager(viewPager)
            }
        } else {
            LocationFragment.newInstance()
            WindFragment.newInstance()
            ForecastFragment.newInstance()
        }
    }

    private fun refreshDataThread(): Thread {
        return object : Thread() {
            override fun run() {
                try {
                    while (!isInterrupted) {
                        runOnUiThread { initializeWeatherData() }
                        Thread.sleep(1000 * 60 * 1000)
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }
    }

    private fun timeThreadCreation(): Thread {
        return object : Thread() {
            override fun run() {
                try {
                    while (!isInterrupted) {
                        runOnUiThread {
                            val timer = findViewById(R.id.timer) as TextView
                            val coordinates = findViewById(R.id.coordinates) as TextView
                            if (timer != null) {
                                timer.text = "Aktualna data: " + DateFormat.getDateTimeInstance().format(Date())
                        }
                    }
                    Thread.sleep(1000)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}

override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_content, menu)
    return true
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val id = item.itemId

    if (id == R.id.action_weather_settings) {
        val intent = Intent(this@MainActivity, WeatherSettingsActivity::class.java)
        startActivity(intent)
        return true
    }
    return super.onOptionsItemSelected(item)
}

private fun initializeWeatherData() {
    if (isOnline(this)) {
        initializeOnlineWeather()
    } else {
        noInternetConnectionInfo()
        initializeOfflineWeather()
    }
    //initializeOfflineWeather()
}

private fun initializeOnlineWeather() {
    val fetchWeatherData = FetchWeatherData()
    fetchWeatherData.execute(
        DefaultValue.system + "",
        DefaultValue.selectedLocation.city,
        DefaultValue.selectedLocation.country
    )
    System.out.println(DefaultValue.selectedLocation.city)
    System.out.println("CALE URL CHYBA DZIALAJACE. : " + DefaultValue.WEATHER_URL[0] + DefaultValue.selectedLocation.city +         //miasto
            "," + DefaultValue.selectedLocation.country +                          //kraj
            DefaultValue.WEATHER_URL[1] + DefaultValue.system +     //jednostki
            DefaultValue.WEATHER_URL[2])


    try {
        DefaultValue.weather = fetchWeatherData.get()
        //saveWeather(DefaultValue.weather)
        //saveLocations(DefaultValue.locations)
    } catch (e: ExecutionException) {
        e.printStackTrace()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }



    //saveWeather(DefaultValue.weather)
    //saveLocations(DefaultValue.locations)
    Toast.makeText(this, R.string.updateData, Toast.LENGTH_SHORT).show()
}

private fun initializeOfflineWeather() {
    DefaultValue.locations = loadLocations()
    DefaultValue.weather = loadWeather()
}

private fun noInternetConnectionInfo() {
    AlertDialog.Builder(this)
        .setTitle("Tryb Offline")
        .setMessage("Brak połączenia z Internetem!")
        .setPositiveButton(
            android.R.string.ok
        ) { dialog, which -> Toast.makeText(applicationContext, R.string.noWeatherInfo, Toast.LENGTH_LONG).show() }
        .show()
}

//LOAD SAVE//

private fun loadWeather(): Weather? {
    var fileInputStream: FileInputStream? = null
    var weather: Weather? = null
    try {
        val file = File(cacheDir.toString() + File.separator + R.string.weatherFile)

        if (file.exists()) {
            fileInputStream = FileInputStream(file)
            val objectInputStream = ObjectInputStream(fileInputStream)
            weather = objectInputStream.readObject() as Weather?
            objectInputStream.close()
        }

    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return weather
}

private fun saveWeather(weather: Weather?) {
    var fileOutputStream: FileOutputStream? = null
    try {
        val file = File(cacheDir.toString() + File.separator + R.string.weatherFile)

        if (file.exists()) {
            file.delete()
        }

        file.createNewFile()
        fileOutputStream = FileOutputStream(file)
        val objectOutputStream = ObjectOutputStream(fileOutputStream)
        objectOutputStream.writeObject(weather)
        objectOutputStream.close()

    } catch (e: IOException) {
        e.printStackTrace()
    }

}

private fun loadLocations(): KnownLocations {
    var fileInputStream: FileInputStream? = null
    var knownLocations = KnownLocations()
    try {
        val file = File(cacheDir.toString() + File.separator + R.string.locationsFile)

        if (file.exists()) {
            fileInputStream = FileInputStream(file)
            val objectInputStream = ObjectInputStream(fileInputStream)
            knownLocations = objectInputStream.readObject() as KnownLocations
            objectInputStream.close()
        }
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    if (knownLocations.getLocationList()!!.isEmpty()) {
        knownLocations = KnownLocations()
        knownLocations.addLocation(Location("lodz", "pl"))
    }
    return knownLocations
}

private fun saveLocations(knownLocations: KnownLocations) {
    var fileOutputStream: FileOutputStream? = null
    try {
        val file = File(cacheDir.toString() + File.separator + R.string.locationsFile)

        if (file.exists()) {
            file.delete()
        }

        file.createNewFile()


        fileOutputStream = FileOutputStream(file)
        val objectOutputStream = ObjectOutputStream(fileOutputStream)
        objectOutputStream.writeObject(knownLocations)
        objectOutputStream.close()

    } catch (e: IOException) {
        e.printStackTrace()
    }

    }

    companion object {

    fun isOnline(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnectedOrConnecting
        }
    }

}