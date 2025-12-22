package com.halimchoukani.kafs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halimchoukani.kafs.data.model.Coffee
import com.halimchoukani.kafs.data.repository.CoffeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class CoffeeViewModel : ViewModel() {

    private val _coffees = MutableStateFlow<List<Coffee>>(emptyList())
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All Coffee")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    val filteredCoffees: StateFlow<List<Coffee>> = combine(_coffees, _searchQuery, _selectedCategory) { coffees, query, category ->
        coffees.filter { coffee ->
            val matchesSearch = coffee.name.contains(query, ignoreCase = true)
            val matchesCategory = if (category == "All Coffee") true else coffee.category.trim().equals(category.trim(), ignoreCase = true)
            matchesSearch && matchesCategory
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

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

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
    fun getCoffeeById(coffeeId: String, onResult: (Coffee?) -> Unit) {
        CoffeeRepository.getCoffeeById(coffeeId, onResult)
    }
    fun onCategorySelect(category: String) {
        _selectedCategory.value = category
    }
}
