package com.example.jmtask.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jmtask.model.MoviesDTO
import com.example.jmtask.repository.Repository
import com.example.jmtask.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class JMViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getData(): StateFlow<ApiState<MoviesDTO>> =
        repository.getData().stateIn(
            scope = viewModelScope,
            initialValue = ApiState.Loading(),
            started = SharingStarted.WhileSubscribed(5000)
        )
}