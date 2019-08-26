package com.example.szaman.kotlinmultiweather.activity


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.szaman.kotlinmultiweather.R
import com.example.szaman.kotlinmultiweather.utility.DefaultValue

/**
 * Created by Szaman on 2017-05-27.
 */
class SettingsActivity : Activity() {

    private var refreshingValue: EditText? = null
    private var dataRefreshingValue: EditText? = null
    private var longitudeValue: EditText? = null
    private var latitudeValue: EditText? = null
    private var saveButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        init()
        initOnClicks()
    }

    override fun onBackPressed() {
        val intent = Intent(this@SettingsActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun init() {
        //refreshingValue = findViewById(R.id.refreshingValue) as EditText
        dataRefreshingValue = findViewById(R.id.dataRefreshingValue) as EditText
        //longitudeValue = findViewById(R.id.longitudeValue) as EditText
        //latitudeValue = findViewById(R.id.latitudeValue) as EditText
        saveButton = findViewById(R.id.saveButton) as Button


        //refreshingValue!!.setText(DefaultValue.defaultRefreshRatio.toString())
        dataRefreshingValue!!.setText(DefaultValue.defaultDataRefreshRatio.toInt().toString())
        //longitudeValue!!.setText(defaultLongitude)//DefaultValue.defaultLongitude.toFloat())
        //latitudeValue!!.setText(defaultLatitude)//(DefaultValue.defaultLatitude)
    }

    private fun initOnClicks() {
        saveButton!!.setOnClickListener {
            val context = applicationContext
            val failureText = "Błędne dane!"
            val noDataText = "Nie podano danych!"
            val successText = "Zapisano!"
            val failureDuration = Toast.LENGTH_LONG
            val successDuration = Toast.LENGTH_SHORT
            var errorsNumber = 0
            val failureToast = Toast.makeText(context, failureText, failureDuration)
            val noDataToast = Toast.makeText(context, noDataText, failureDuration)
            val successToast = Toast.makeText(context, successText, successDuration)

            //brak danych.
            /*if (refreshingValue!!.text.toString().isEmpty() == true) {
                noDataToast.show()
                errorsNumber++
            }*/
            if (dataRefreshingValue!!.text.toString().isEmpty() == true) {
                noDataToast.show()
                errorsNumber++
            }
            /*else if (latitudeValue!!.text.toString().isEmpty() == true) {
                noDataToast.show()
                errorsNumber++
            } else if (longitudeValue!!.text.toString().isEmpty() == true) {
                noDataToast.show()
                errorsNumber++
            }
            */else {
                //bledne dane.
                /*
                if (Integer.parseInt(refreshingValue!!.text.toString()) < 1) {
                    failureToast.show()
                    errorsNumber++
                }
                */
                if (Integer.parseInt(dataRefreshingValue!!.text.toString()) < 1) {
                    failureToast.show()
                    errorsNumber++
                }
                /*
                if (java.lang.Double.parseDouble(latitudeValue!!.text.toString()) < -90 || java.lang.Double.parseDouble(
                        latitudeValue!!.text.toString()
                    ) > 90
                ) {
                    failureToast.show()
                    errorsNumber++
                }
                if (java.lang.Double.parseDouble(longitudeValue!!.text.toString()) < -180 || java.lang.Double.parseDouble(
                        longitudeValue!!.text.toString()
                    ) > 180
                )

                {

                    failureToast.show()
                    errorsNumber++
                }
                */
                else {
                    //wszystko w porzadku.
                    if (errorsNumber == 0) {
                        successToast.show()
                        //DefaultValue!!.defaultRefreshRatio = Integer.parseInt(refreshingValue!!.text.toString())
                        DefaultValue!!.defaultDataRefreshRatio = dataRefreshingValue.toString().toLong()
                        //DefaultValue!!.defaultLatitude = java.lang.Double.parseDouble(latitudeValue!!.text.toString())
                        //DefaultValue!!.defaultLongitude = java.lang.Double.parseDouble(longitudeValue!!.text.toString())

                        val intent = Intent(this@SettingsActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

}