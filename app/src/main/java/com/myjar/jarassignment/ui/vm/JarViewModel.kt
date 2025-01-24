package com.myjar.jarassignment.ui.vm

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myjar.jarassignment.NetWorkUtils
import com.myjar.jarassignment.data.local.CachedItemDao
import com.myjar.jarassignment.data.model.ComputerItem
import com.myjar.jarassignment.data.repository.JarRepository
import com.myjar.jarassignment.data.repository.JarRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JarViewModel(
    private val repository: JarRepository,
    private val networkUtils: NetWorkUtils
) : ViewModel() {

    private val _listStringData = MutableStateFlow<List<ComputerItem>>(emptyList())
    val listStringData: StateFlow<List<ComputerItem>> = _listStringData

    private val _isCached = MutableStateFlow(false)
    val isCached: StateFlow<Boolean> = _isCached

    @RequiresApi(Build.VERSION_CODES.M)
    fun fetchData(context: Context) {
        viewModelScope.launch {
            val isOnline = networkUtils.isOnline(context)
            repository.fetchResults(isOnline).collect { items ->
                _listStringData.value = items
                _isCached.value = !isOnline
            }
        }
    }
}



