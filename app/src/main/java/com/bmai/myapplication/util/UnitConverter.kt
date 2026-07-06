package com.bmai.myapplication.util

import kotlin.math.roundToInt

object UnitConverter {
    // Height: CM to Feet/Inches
    fun cmToFeetInches(cm: Float): Pair<Int, Int> {
        val totalInches = cm / 2.54f
        val feet = (totalInches / 12).toInt()
        val inches = (totalInches % 12).roundToInt()
        return if (inches == 12) (feet + 1) to 0 else feet to inches
    }

    fun feetInchesToCm(feet: Int, inches: Int): Float {
        val totalInches = (feet * 12) + inches
        return totalInches * 2.54f
    }

    // Weight: KG to LBS
    fun kgToLbs(kg: Float): Float = kg * 2.20462f

    fun lbsToKg(lbs: Float): Float = lbs / 2.20462f
}
