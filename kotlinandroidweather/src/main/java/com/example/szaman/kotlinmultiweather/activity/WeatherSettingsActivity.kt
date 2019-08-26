package com.example.szaman.kotlinmultiweather.activity


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream
import java.util.*
import com.example.szaman.kotlinmultiweather.R
import common.szaman.model.KnownLocations
import common.szaman.model.Location
import com.example.szaman.kotlinmultiweather.utility.DefaultValue

/**
 * Created by Szaman on 2017-06-22.
 */

class WeatherSettingsActivity : Activity() {

    private var cityNameValue: TextView? = null

    private var newLocationValue: EditText? = null

    private var unitTypeSpinner: Spinner? = null
    private var allLocationsSpinner: Spinner? = null

    private var refreshButton: Button? = null
    private var saveButton: Button? = null
    private var destroyButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_settings)
        init()
        initOnClicks()
    }

    override fun onBackPressed() {
        val intent = Intent(this@WeatherSettingsActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun init() {
        val currentIndex = DefaultValue.locations.getLocationList()!!.size - 1

        cityNameValue = findViewById(R.id.cityNameValue) as TextView
        unitTypeSpinner = findViewById(R.id.unitTypeSpinner) as Spinner
        allLocationsSpinner = findViewById(R.id.allLocationsSpinner) as Spinner

        newLocationValue = findViewById(R.id.newLocationValue) as EditText

        saveButton = findViewById(R.id.saveButton) as Button
        refreshButton = findViewById(R.id.refreshButton) as Button
        destroyButton = findViewById(R.id.destroyButton) as Button

        val location: Location = DefaultValue.locations.getLocationList()!!.get(0)

        cityNameValue!!.text = location.city + ", " + location.country

        val locationsArray = ArrayList<String>()

        for (i in 0..currentIndex) {
            val currentInjectingLocation: Location = DefaultValue!!.locations.getLocationList()!!.get(i)
            locationsArray.add("" + currentInjectingLocation.city + ", " + currentInjectingLocation.country)
        }

        val locationsAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            locationsArray
        ) //selected item will look like a spinner set from XML
        locationsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        allLocationsSpinner!!.adapter = locationsAdapter


        val unitTypeAdapter =
            ArrayAdapter.createFromResource(this, R.array.unitList, android.R.layout.simple_spinner_item)
        unitTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        unitTypeSpinner!!.adapter = unitTypeAdapter
        unitTypeSpinner!!.setSelection(if (DefaultValue!!.system === "metric") 0 else 1)
    }

    private fun initOnClicks() {
        refreshButton!!.setOnClickListener {
            val context = applicationContext
            val refreshText = "Refresh!"
            val refreshDuration = Toast.LENGTH_SHORT
            val refreshToast = Toast.makeText(context, refreshText, refreshDuration)
            refreshToast.show()
            val chosenCity = allLocationsSpinner!!.selectedItem.toString()
            val partedUserChoice = chosenCity.split("\\, ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val cityName = partedUserChoice[0]
            val countryCode = partedUserChoice[1]
            DefaultValue!!.selectedLocation =
                    Location(cityName, countryCode)// text = spinner.getSelectedItem().toString();
            cityNameValue!!.text = "" + DefaultValue!!.selectedLocation.city + ", " + DefaultValue!!.selectedLocation.country
            if ("Metryczny" == unitTypeSpinner!!.selectedItem.toString()) {
                DefaultValue!!.system = "metric"
            } else {
                DefaultValue!!.system = "imperial"
            }
        }

        saveButton!!.setOnClickListener {
            val context = applicationContext
            if (newLocationValue!!.text.toString().length == 0) {
                val failureText = "Nie podano danych!"
                val failureDuration = Toast.LENGTH_SHORT
                val failureToast = Toast.makeText(context, failureText, failureDuration)
                failureToast.show()
            } else {
                val saveText = "Dane zapisane!"
                val saveDuration = Toast.LENGTH_SHORT
                val saveToast = Toast.makeText(context, saveText, saveDuration)
                saveToast.show()

                val chosenCity = newLocationValue!!.text.toString()
                val partedUserChoice = chosenCity.split("\\, ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val cityName = partedUserChoice[0]
                val countryCode = partedUserChoice[1]
                val newLocation = Location(cityName, countryCode)

                DefaultValue!!.locations.addLocation(newLocation)
                saveLocations(DefaultValue!!.locations)

                val intent = Intent(this@WeatherSettingsActivity, WeatherSettingsActivity::class.java)
                startActivity(intent)
            }
        }

        destroyButton!!.setOnClickListener {
            val context = applicationContext
            val destroyText = "Dane zresetowane!"
            val destroyDuration = Toast.LENGTH_SHORT
            val destroyToast = Toast.makeText(context, destroyText, destroyDuration)
            destroyToast.show()
            DefaultValue!!.locations = KnownLocations()
            DefaultValue!!.locations.addLocation(DefaultValue!!.defaultLocation)
            saveLocations(DefaultValue!!.locations)

            val intent = Intent(this@WeatherSettingsActivity, WeatherSettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
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
}