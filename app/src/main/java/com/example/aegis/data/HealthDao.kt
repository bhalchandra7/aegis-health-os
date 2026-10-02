package com.example.aegis.model

data class HealthUiState(
    val profile: EmergencyProfile = EmergencyProfile(),
    val medications: List<Medication> = emptyList(),
    val appointments: List<Appointment> = emptyList()
)
