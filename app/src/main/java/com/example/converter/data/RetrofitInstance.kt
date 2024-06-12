package com.example.converter.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Объект для создания экземпляра Retrofit
object RetrofitInstance {
    // Создаем объект, который будет логировать HTTP запросы и ответы
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    // Создаем HTTP клиент и добавляем к нему логирование
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Настраиваем Retrofit для работы с API
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/v6/") // Базовый URL для всех запросов
            .client(client) // Добавляем наш HTTP клиент
            .addConverterFactory(GsonConverterFactory.create()) // Добавляем преобразователь JSON
            .build()
    }

    // Создаем объект API для выполнения запросов
    val api: CurrencyApi by lazy {
        retrofit.create(CurrencyApi::class.java) // Создаем реализацию API интерфейса
    }
}


