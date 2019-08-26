package com.szaman.example.utility


/**
 * Created by Szaman on 2017-06-28.
 */

object ImageChooser {

    fun getImagePath(code: String): String {
        when (Integer.parseInt(code)) {
            200 -> return "img11d.png"
            201 -> return "img11d.png"
            202 -> return "img11d.png"
            210 -> return "img11d.png"
            211 -> return "img11d.png"
            212 -> return "img11d.png"
            221 -> return "img11d.png"
            230 -> return "img11d.png"
            231 -> return "img11d.png"

            232 -> return "img11d.png"
            300 -> return "img09d.png"
            301 -> return "img09d.png"
            302 -> return "img09d.png"
            310 -> return "img09d.png"
            311 -> return "img09d.png"
            312 -> return "img09d.png"
            313 -> return "img09d.png"
            314 -> return "img09d.png"
            321 -> return "img09d.png"

            500 -> return "img10d.png"
            501 -> return "img10d.png"
            502 -> return "img10d.png"
            503 -> return "img10d.png"
            504 -> return "img10d.png"
            511 -> return "img13d.png"
            520 -> return "img09d.png"
            521 -> return "img09d.png"
            522 -> return "img09d.png"
            531 -> return "img09d.png"

            600 -> return "img13d.png"
            601 -> return "img13d.png"
            602 -> return "img13d.png"
            611 -> return "img13d.png"
            612 -> return "img13d.png"
            615 -> return "img13d.png"
            616 -> return "img13d.png"
            620 -> return "img13d.png"
            621 -> return "img13d.png"
            622 -> return "img13d.png"

            701 -> return "img50d.png"
            711 -> return "img50d.png"
            721 -> return "img50d.png"
            731 -> return "img50d.png"
            741 -> return "img50d.png"
            751 -> return "img50d.png"
            761 -> return "img50d.png"
            762 -> return "img50d.png"
            771 -> return "img50d.png"
            781 -> return "img50d.png"

            800 -> return "img01d.png"
            802 -> return "img02d.png"
            803 -> return "img04d.png"
            805 -> return "img04d.png"

            3200 -> return "img3200.png"
            else -> return "img3200.png"
        }
    }
}

