package com.example.invisiaproject.ui

data class HotelRegionModel(
    val message: String? = null,
    var body: HoteRegionBody? = null
)

data class HoteRegionBody(
    val hotels: ArrayList<Hotels>? = null,
    val regions: ArrayList<Regions>? = null
)

data class Hotels(
    val name: String? = null
)

data class Regions(
    val name: String? = null
)