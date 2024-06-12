package com.example.converter.presentation.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.DialogFragment
import com.example.converter.R

// Фрагмент диалога для выбора валюты
class CurrencySelectorFragment(private val onCurrencySelected: (String) -> Unit) :
    DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.select_spinner, container, false)
        val listView: ListView = view.findViewById(R.id.list_view)
        val editText: AppCompatEditText = view.findViewById(R.id.edit_text)

        // Список всех доступных валют
        val currencies =
            listOf("USD","AED","AFN","ALL","AMD","ANG","AOA","ARS","AUD","AWG","AZN","BAM","BBD","BDT","BGN","BHD","BIF","BMD","BND","BOB","BRL","BSD","BTN","BWP","BYN","BZD","CAD","CDF","CHF","CLP","CNY","COP","CRC","CUP","CVE","CZK","DJF","DKK","DOP","DZD","EGP","ERN","ETB","EUR","FJD","FKP","FOK","GBP","GEL","GGP","GHS","GIP","GMD","GNF","GTQ","GYD","HKD","HNL","HRK","HTG","HUF","IDR","ILS","IMP","INR","IQD","IRR","ISK","JEP","JMD","JOD","JPY","KES","KGS","KHR","KID","KMF","KRW","KWD","KYD","KZT","LAK","LBP","LKR","LRD","LSL","LYD","MAD","MDL","MGA","MKD","MMK","MNT","MOP","MRU","MUR","MVR","MWK","MXN","MYR","MZN","NAD","NGN","NIO","NOK","NPR","NZD","OMR","PAB","PEN","PGK","PHP","PKR","PLN","PYG","QAR","RON","RSD","RUB","RWF","SAR","SBD","SCR","SDG","SEK","SGD","SHP","SLE","SLL","SOS","SRD","SSP","STN","SYP","SZL","THB","TJS","TMT","TND","TOP","TRY","TTD","TVD","TWD","TZS","UAH","UGX","UYU","UZS","VES","VND","VUV","WST","XAF","XCD","XDR","XOF","XPF","YER","ZAR","ZMW","ZWL")

        // Адаптер для списка валют
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, currencies)
        listView.adapter = adapter

        // Обработчик выбора элемента из списка
        listView.setOnItemClickListener { _, _, position, _ ->
            onCurrencySelected(adapter.getItem(position) ?: "") // Возвращаем выбранную валюту
            dismiss() // Закрываем диалог
        }

        // Добавляем слушателя изменения текста для фильтрации списка валют
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s) // Фильтруем адаптер по введенному тексту

            }
            override fun afterTextChanged(s: Editable?) {}
        })
        return view
    }
}
