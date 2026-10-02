package com.example.aegis.navigation

sealed class AppDestination(val route: String) {
    object Home : AppDestination("home")
    object Health : AppDestination("health")
    object Medications : AppDestination("medications")
    object Appointments : AppDestination("appointments")
    object Profile : AppDestination("profile")
    object EditProfile : AppDestination("edit_profile")
    object EditMedication : AppDestination("edit_medication")
    object EditAppointment : AppDestination("edit_appointment")
}
