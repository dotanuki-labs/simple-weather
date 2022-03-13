package io.dotanuki.demos.weather.domain

object ComputeRainExpectation {

    operator fun invoke(probability: Float): RainExpectation =
        when {
            probability < 0.0 -> throw IllegalArgumentException("Cannot compute with negative probability")
            probability > 1.0 -> throw IllegalArgumentException("Max probabiblity must be 100%")
            probability > 0.5 -> RainExpectation.HIGH
            else -> RainExpectation.LOW
        }
}
