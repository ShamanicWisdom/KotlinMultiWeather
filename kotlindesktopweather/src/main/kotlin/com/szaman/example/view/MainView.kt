package com.szaman.example.view

import com.szaman.example.app.Styles
import com.szaman.example.app.fetchData
import com.szaman.example.utility.DefaultValue
import com.szaman.example.utility.ImageChooser.getImagePath
import common.szaman.model.Location
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.*
import javafx.scene.layout.VBox
import tornadofx.*
import kotlin.system.exitProcess

class MainView : View("Tornado DesktopWeather")
{
    var locationName = "Lodz, PL"
    var locationNameField: TextField by singleAssign()

    var day1Name = SimpleStringProperty()
    var day1Temperature = SimpleStringProperty()
    var day1ImageURL = SimpleStringProperty()

    var day2Name = SimpleStringProperty()
    var day2Temperature = SimpleStringProperty()
    var day2ImageURL = SimpleStringProperty()

    var day3Name = SimpleStringProperty()
    var day3Temperature = SimpleStringProperty()
    var day3ImageURL = SimpleStringProperty()

    var day4Name = SimpleStringProperty()
    var day4Temperature = SimpleStringProperty()
    var day4ImageURL = SimpleStringProperty()

    var day5Name = SimpleStringProperty()
    var day5Temperature = SimpleStringProperty()
    var day5ImageURL = SimpleStringProperty()

    private var locationLabelValue = SimpleStringProperty(DefaultValue.selectedLocation.city + ", " + DefaultValue.selectedLocation.country)
    override val root = VBox()

    init {
        DefaultValue.weather = fetchData(DefaultValue.system + "", DefaultValue.selectedLocation.city, DefaultValue.selectedLocation.country)
        setValues()
        with(root) {
            hbox {
                addClass(Styles.headerHbox)
                label("Tornado DesktopWeather")
                {
                    addClass(Styles.headerLabel)
                }
            }

            hbox {
                addClass(Styles.spacingHbox)
                label("Podaj lokację (Miasto, kodPaństwa): ")
                {
                    addClass(Styles.label)
                }
                locationNameField = textfield()
                {
                    textProperty().addListener { _, _, new ->
                        locationName = new
                    }
                }
                button("Wyślij Zapytanie")
                {
                    addClass(Styles.pingButton)
                    action {
                        if (locationNameField.length == 0)
                        {
                            val failureText = "Nie podano danych!"
                            tornadofx.alert(
                                    type = Alert.AlertType.WARNING,
                                    header = "Błąd",
                                    content = failureText
                            )
                        }
                        else
                        {
                            val chosenCity = locationName
                            val partedUserChoice = chosenCity.split("\\, ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            val cityName = partedUserChoice[0]
                            val countryCode = partedUserChoice[1]
                            val newLocation = Location(cityName, countryCode)
                            DefaultValue!!.selectedLocation = newLocation
                            println("URL: " + DefaultValue.WEATHER_URL[0] + DefaultValue.selectedLocation.city + "," + DefaultValue.selectedLocation.country + DefaultValue.WEATHER_URL[1] + com.szaman.example.utility.DefaultValue.system + com.szaman.example.utility.DefaultValue.WEATHER_URL[2])
                            DefaultValue.weather = fetchData(DefaultValue.system + "", DefaultValue.selectedLocation.city, DefaultValue.selectedLocation.country)
                            locationLabelValue.set(chosenCity)
                            setValues()
                        }
                    }

                }
            }

            hbox {
                addClass(Styles.leftHbox)
                label("Aktualna lokacja: ")
                {
                    addClass(Styles.label)
                }

                label()
                {
                    bind(locationLabelValue)
                    addClass(Styles.label)
                }
            }

            hbox {
                addClass(Styles.emptySpaceHBox)
            }

            //Opis
            hbox {
                addClass(Styles.headerHbox)
                label("Data")
                {
                    addClass(Styles.centerLabel)
                }
                label("Temperatura")
                {
                    addClass(Styles.centerLabel)
                }
                label("Obrazek")
                {
                    addClass(Styles.centerLabel)
                }
            }

            //1 dzien
            hbox {
                addClass(Styles.headerHbox)
                label()
                {
                    bind(day1Name)
                    addClass(Styles.centerLabel)
                }
                label()
                {
                    bind(day1Temperature)
                    addClass(Styles.centerLabel)
                }
                imageview(day1ImageURL.value)
            }

            //2 dzien
            hbox {
                addClass(Styles.headerHbox)
                label()
                {
                    bind(day2Name)
                    addClass(Styles.centerLabel)
                }
                label()
                {
                    bind(day2Temperature)
                    addClass(Styles.centerLabel)
                }
                imageview(day2ImageURL.value)
            }

            //3 dzien
            hbox {
                addClass(Styles.headerHbox)
                label()
                {
                    bind(day3Name)
                    addClass(Styles.centerLabel)
                }
                label()
                {
                    bind(day3Temperature)
                    addClass(Styles.centerLabel)
                }
                imageview(day3ImageURL.value)
            }

            //4 dzien
            hbox {
                addClass(Styles.headerHbox)
                label()
                {
                    bind(day4Name)
                    addClass(Styles.centerLabel)
                }
                label()
                {
                    bind(day4Temperature)
                    addClass(Styles.centerLabel)
                }
                imageview(day4ImageURL.value)
            }

            //5 dzien
            hbox {
                addClass(Styles.headerHbox)
                label()
                {
                    bind(day5Name)
                    addClass(Styles.centerLabel)
                }
                label()
                {
                    bind(day5Temperature)
                    addClass(Styles.centerLabel)
                }

                imageview(day5ImageURL.value)
            }

            hbox {
                addClass(Styles.emptySpaceHBox)
            }

            hbox {
                addClass(Styles.headerHbox)
                button("Wyjście"){
                    action{
                        exitProcess(0)
                    }
                }
            }
        }
    }

    private fun setValues()
    {
        var temperatureSymbol: String? = null
        if(DefaultValue.system.equals("metric"))
        {
            temperatureSymbol = DefaultValue.celsiusSymbol
        }
        else
        {
            temperatureSymbol = DefaultValue.fahrenheitSymbol
        }

        day1Name.set("" + DefaultValue.weather?.forecast?.get(0)?.day.toString())
        day1Temperature.set("" + DefaultValue.weather?.forecast?.get(0)?.minimalTemperature.toString() + temperatureSymbol + " - "
                        + DefaultValue.weather?.forecast?.get(0)?.maximalTemperature.toString() + temperatureSymbol)
        day1ImageURL.set(getImagePath(DefaultValue.weather?.forecast?.get(0)?.imageCode.toString()))

        day2Name.set("" + DefaultValue.weather?.forecast?.get(1)?.day.toString())
        day2Temperature.set("" + DefaultValue.weather?.forecast?.get(1)?.minimalTemperature.toString() + temperatureSymbol + " - "
                        + DefaultValue.weather?.forecast?.get(1)?.maximalTemperature.toString() + temperatureSymbol)
        day2ImageURL.set(getImagePath(DefaultValue.weather?.forecast?.get(1)?.imageCode.toString()))

        day3Name.set("" + DefaultValue.weather?.forecast?.get(2)?.day.toString())
        day3Temperature.set("" + DefaultValue.weather?.forecast?.get(2)?.minimalTemperature.toString() + temperatureSymbol + " - "
                        + DefaultValue.weather?.forecast?.get(2)?.maximalTemperature.toString() + temperatureSymbol)
        day3ImageURL.set(getImagePath(DefaultValue.weather?.forecast?.get(2)?.imageCode.toString()))

        day4Name.set("" + DefaultValue.weather?.forecast?.get(3)?.day.toString())
        day4Temperature.set("" + DefaultValue.weather?.forecast?.get(3)?.minimalTemperature.toString() + temperatureSymbol + " - "
                        + DefaultValue.weather?.forecast?.get(3)?.maximalTemperature.toString() + temperatureSymbol)
        day4ImageURL.set(getImagePath(DefaultValue.weather?.forecast?.get(3)?.imageCode.toString()))

        day5Name.set("" + DefaultValue.weather?.forecast?.get(4)?.day.toString())
        day5Temperature.set("" + DefaultValue.weather?.forecast?.get(4)?.minimalTemperature.toString() + temperatureSymbol + " - "
                        + DefaultValue.weather?.forecast?.get(4)?.maximalTemperature.toString() + temperatureSymbol)
        day5ImageURL.set(getImagePath(DefaultValue.weather?.forecast?.get(4)?.imageCode.toString()))


    }

}