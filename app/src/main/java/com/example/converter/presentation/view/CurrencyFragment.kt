package com.example.converter.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.converter.databinding.FragmentConverterBinding
import com.example.converter.presentation.viewmodel.CurrencyViewModel

class CurrencyFragment : Fragment() {

    private lateinit var binding: FragmentConverterBinding
    private val viewModel: CurrencyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConverterBinding.inflate(inflater, container, false)

        setupViews() // Настройка слушателей для элементов UI
        observeViewModel() // Подписка на изменения данных ViewModel

        return binding.root
    }

    private fun setupViews() {
        // Устанавливаем обработчик нажатия для кнопки конвертации
        binding.buttonConvert.setOnClickListener {
            convertCurrency()
        }

        // Устанавливаем обработчики нажатий для выпадающих списков валют
        binding.fromDropdown.setOnClickListener { showCurrencySelector(true) }
        binding.toDropdown.setOnClickListener { showCurrencySelector(false) }
    }

    private fun observeViewModel() {
        // Подписываемся на изменения конверсионного курса
        viewModel.conversionRate.observe(viewLifecycleOwner) { rate ->
            binding.conversionRate.text = rate
        }

        // Подписываемся на изменения сообщений об ошибках
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        // Подписываемся на изменения выбранной исходной валюты
        viewModel.fromCurrency.observe(viewLifecycleOwner) { fromCurrency ->
            binding.fromDropdown.text = fromCurrency
        }

        // Подписываемся на изменения выбранной целевой валюты
        viewModel.toCurrency.observe(viewLifecycleOwner){ toCurrency ->
            binding.toDropdown.text = toCurrency
        }

        // Подписываемся на изменения введенной суммы
        viewModel.amount.observe(viewLifecycleOwner) { amount ->
            binding.editTextAmount.setText(amount)
        }
    }

    // Показать диалог выбора валюты
    private fun showCurrencySelector(isFrom: Boolean) {
        val dialog = CurrencySelectorFragment { currency ->
            if (isFrom) {
                binding.fromDropdown.text = currency
                viewModel.setFromCurrency(currency) // Устанавливаем выбранную исходную валюту в ViewModel
            } else {
                binding.toDropdown.text = currency
                viewModel.setToCurrency(currency) // Устанавливаем выбранную целевую валюту в ViewModel
            }
        }
        dialog.show(childFragmentManager, "currencySelector")
    }

    // Конвертация валюты
    private fun convertCurrency() {
        val fromCurrency = binding.fromDropdown.text.toString()
        val toCurrency = binding.toDropdown.text.toString()
        val amount = binding.editTextAmount.text.toString()

        // Проверяем, заполнены ли все поля
        if (fromCurrency.isEmpty() || toCurrency.isEmpty() || amount.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Сохраняем введенную сумму в ViewModel
        viewModel.setAmount(amount)
        // Получаем результат
        viewModel.getConversionRate(fromCurrency, toCurrency, amount.toDouble())
    }
}
