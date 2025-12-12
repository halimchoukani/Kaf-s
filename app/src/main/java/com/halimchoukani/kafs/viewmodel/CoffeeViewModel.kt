package com.halimchoukani.kafs.viewmodel

import androidx.lifecycle.ViewModel
import com.halimchoukani.kafs.data.model.Coffee
import com.halimchoukani.kafs.data.repository.CoffeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CoffeeViewModel : ViewModel() {

    private val _coffees = MutableStateFlow<List<Coffee>>(emptyList())
    val coffees: StateFlow<List<Coffee>> = _coffees

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadCoffees()
    }

    private fun loadCoffees() {
        _isLoading.value = true
        _error.value = null

        CoffeeRepository.getCoffees { list ->
            if (list != null) {
                _coffees.value = list
                _isLoading.value = false
            } else {
                _error.value = "Failed to load coffees"
                _isLoading.value = false
            }
        }
    }
}
