package com.example.converter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converter.domain.CurrencyRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CurrencyViewModel : ViewModel() {

    private val _conversionRate = MutableLiveData<String>()
    val conversionRate: LiveData<String> get() = _conversionRate

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _fromCurrency = MutableLiveData<String>()
    val fromCurrency: LiveData<String> get() = _fromCurrency

    private val _toCurrency = MutableLiveData<String>()
    val toCurrency: LiveData<String> get() = _toCurrency

    private val _amount = MutableLiveData<String>()
    val amount: LiveData<String> get() = _amount

    // Экземпляр репозитория для получения данных
    private val repository = CurrencyRepository()
    private val apiKey = "349ea87adbc9538b0aeb91c0" // API ключ

    // Метод для получения курса конверсии
    fun getConversionRate(fromCurrency: String, toCurrency: String, amount: Double) {
        viewModelScope.launch {
            try {
                // Запрашиваем курс конверсии из репозитория
                val response = repository.getRates(apiKey, fromCurrency)
                val rate = response.conversion_rates[toCurrency]
                if (rate != null) {
                    // Вычисляем и обновляем значение курса конверсии
                    val convertedAmount = amount * rate
                    _conversionRate.value = String.format("%.2f %s", convertedAmount, toCurrency)
                } else {
                    // Обновляем сообщение об ошибке, если курс не найден
                    _errorMessage.value = "Invalid currency"
                }
            } catch (e: HttpException) {
                // Обрабатываем ошибки HTTP
                _errorMessage.value = "Error: ${e.message}"
            } catch (e: IOException) {
                // Обрабатываем ошибки сети
                _errorMessage.value = "Network error. Please try again."
            } catch (e: Exception) {
                // Обрабатываем другие ошибки
                _errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    // Устанавливаем исходную валюту
    fun setFromCurrency(currency: String) {
        _fromCurrency.value = currency
    }

    // Устанавливаем целевую валюту
    fun setToCurrency(currency: String) {
        _toCurrency.value = currency
    }

    // Устанавливаем сумму
    fun setAmount(amount: String) {
        _amount.value = amount
    }
}
