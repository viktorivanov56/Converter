package com.example.converter.domain

import com.example.converter.data.CurrencyResponse
import com.example.converter.data.RetrofitInstance

// Репозиторий для получения данных о валютных курсах
class CurrencyRepository {

    // Функция для получения курсов валют с помощью API
    // Использует ключ API и базовую валюту для запроса
    suspend fun getRates(apiKey: String, base: String): CurrencyResponse {
        // Возвращает результат вызова API из инстанса Retrofit
        return RetrofitInstance.api.getRates(apiKey, base)
    }
}
