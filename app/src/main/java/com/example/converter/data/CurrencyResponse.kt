package com.example.converter.data

data class CurrencyResponse(
    val conversion_rates: Map<String, Double>,
    val base_code: String,
    val time_last_update_utc: String
)
