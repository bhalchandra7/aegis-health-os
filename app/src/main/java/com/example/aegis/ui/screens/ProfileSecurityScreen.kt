package com.example.aegis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aegis.data.HealthRepository
import com.example.aegis.model.Appointment
import com.example.aegis.model.EmergencyProfile
import com.example.aegis.model.HealthUiState
import com.example.aegis.model.Medication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HealthViewModel(
    private val repository: HealthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HealthUiState())
    val uiState: StateFlow<HealthUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedDefaultData()
            repository.observeHealthState().collect { state ->
                _uiState.value = state
            }
        }
    }

    fun updateProfile(profile: EmergencyProfile) {
        viewModelScope.launch {
            repository.updateProfile(profile)
        }
    }

    fun addMedication(medication: Medication) {
        viewModelScope.launch {
            repository.addMedication(medication)
        }
    }

    fun addAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.addAppointment(appointment)
        }
    }
}
