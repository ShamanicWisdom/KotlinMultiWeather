package common.szaman.model

/**
 * Created by Szaman on 2017-06-21.
 */

class KnownLocations {

    var knownLocationList: MutableList<Location>? = null

    val array: Array<String?>
        get() {
            val locationsArray = arrayOfNulls<String>(knownLocationList!!.size)
            for (i in knownLocationList!!.indices) {
                locationsArray[i] = knownLocationList!![i].toString()
            }
            return locationsArray
        }

    constructor() {
        this.knownLocationList = ArrayList<Location>()
    }

    constructor(localizations: MutableList<Location>) {
        this.knownLocationList = localizations
    }

    fun addLocation(location: Location) {
        if (knownLocationList == null) {
            knownLocationList = ArrayList<Location>()
        }
        knownLocationList!!.add(location)
    }

    fun getLocationList(): List<Location>? {
        return knownLocationList
    }

    fun setLocationList(locationList: MutableList<Location>) {
        this.knownLocationList = locationList
    }

    fun isLocationExists(location: String?): Boolean {
        if (location == null || knownLocationList!!.size == 0) {
            return false
        }
        for (locationFromArray in array) {
            if (locationFromArray == location) {
                return true
            }
        }
        return false
    }
}