package com.example.converter.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.converter.databinding.FragmentSettingsBinding
import com.example.converter.presentation.viewmodel.SettingsViewModel


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        setupObservers()
        setupListeners()
        return binding.root
    }

    // Метод для настройки наблюдателей LiveData
    private fun setupObservers() {
        // Наблюдаем за изменениями темы (темная или светлая)
        viewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            // Устанавливаем состояние переключателя темы в зависимости от текущей темы
            binding.switchTheme.isChecked = isDark
        }
    }

    // Метод для настройки слушателей событий
    private fun setupListeners() {
        // Добавляем слушатель для переключателя темы
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            // При изменении состояния переключателя вызываем метод во ViewModel для переключения темы
            viewModel.toggleTheme(isChecked)
        }
    }
}
