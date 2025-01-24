package com.myjar.jarassignment.ui.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myjar.jarassignment.AppModule
import com.myjar.jarassignment.data.local.CachedItemDao
import com.myjar.jarassignment.data.model.ComputerItem
import com.myjar.jarassignment.data.repository.JarRepository
import com.myjar.jarassignment.data.repository.JarRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JarViewModel(
    private val repository: JarRepository
) : ViewModel() {

    private val _listStringData = MutableStateFlow<List<ComputerItem>>(emptyList())
    val listStringData: StateFlow<List<ComputerItem>> = _listStringData

    private val _isCached = MutableStateFlow(false)
    val isCached: StateFlow<Boolean> = _isCached

    fun fetchData() {
        viewModelScope.launch {
            try {
                repository.fetchResults().collect { items ->
                    _listStringData.value = items
                    _isCached.value = items.isNotEmpty()
                }
            } catch (e: Exception) {
                Log.e("JarViewModel", "Error fetching data: ${e.message}")
            }
        }
    }

    private fun isOnline(): Boolean {

        return true
    }
}
