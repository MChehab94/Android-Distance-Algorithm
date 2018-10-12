package mchehab.com.kotlin.distanceAlgorithm

import kotlin.math.abs
import kotlin.math.pow

class MinkowskiDistance(var p: Int): DistanceAlgorithm {
    override fun calculateDistance(x1: Double, y1: Double, x2: Double, y2: Double): Double {
        val x = (x1 - x2).pow(p)
        val y = (y1 - y2).pow(p)
        val distance = abs(x - y).pow(1.0/p)
        return distance
    }
}