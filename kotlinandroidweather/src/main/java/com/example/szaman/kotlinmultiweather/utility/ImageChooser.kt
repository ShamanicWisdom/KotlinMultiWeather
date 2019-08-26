package com.example.szaman.kotlinmultiweather.utility

import com.example.szaman.kotlinmultiweather.R

/**
 * Created by Szaman on 2017-06-28.
 */

object ImageChooser {

    fun getImage(code: String): Int {
        when (Integer.parseInt(code)) {
            200 -> return R.drawable.img11d
            201 -> return R.drawable.img11d
            202 -> return R.drawable.img11d
            210 -> return R.drawable.img11d
            211 -> return R.drawable.img11d
            212 -> return R.drawable.img11d
            221 -> return R.drawable.img11d
            230 -> return R.drawable.img11d
            231 -> return R.drawable.img11d

            232 -> return R.drawable.img11d
            300 -> return R.drawable.img09d
            301 -> return R.drawable.img09d
            302 -> return R.drawable.img09d
            310 -> return R.drawable.img09d
            311 -> return R.drawable.img09d
            312 -> return R.drawable.img09d
            313 -> return R.drawable.img09d
            314 -> return R.drawable.img09d
            321 -> return R.drawable.img09d

            500 -> return R.drawable.img10d
            501 -> return R.drawable.img10d
            502 -> return R.drawable.img10d
            503 -> return R.drawable.img10d
            504 -> return R.drawable.img10d
            511 -> return R.drawable.img13d
            520 -> return R.drawable.img09d
            521 -> return R.drawable.img09d
            522 -> return R.drawable.img09d
            531 -> return R.drawable.img09d

            600 -> return R.drawable.img13d
            601 -> return R.drawable.img13d
            602 -> return R.drawable.img13d
            611 -> return R.drawable.img13d
            612 -> return R.drawable.img13d
            615 -> return R.drawable.img13d
            616 -> return R.drawable.img13d
            620 -> return R.drawable.img13d
            621 -> return R.drawable.img13d
            622 -> return R.drawable.img13d

            701 -> return R.drawable.img50d
            711 -> return R.drawable.img50d
            721 -> return R.drawable.img50d
            731 -> return R.drawable.img50d
            741 -> return R.drawable.img50d
            751 -> return R.drawable.img50d
            761 -> return R.drawable.img50d
            762 -> return R.drawable.img50d
            771 -> return R.drawable.img50d
            781 -> return R.drawable.img50d

            800 -> return R.drawable.img01d
            802 -> return R.drawable.img02d
            803 -> return R.drawable.img04d
            805 -> return R.drawable.img04d

            3200 -> return R.drawable.img3200
            else -> return R.drawable.img3200
        }
    }
}

