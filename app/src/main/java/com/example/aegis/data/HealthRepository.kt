package com.example.aegis.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val bloodType: String,
    val allergies: String,
    val currentMedications: String,
    val conditions: String,
    val physician: String,
    val directives: String,
    val emergencyContacts: String,
    val implantedDevices: String
)

@Entity(tableName = "medications")
data class MedicationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val dosage: String,
    val schedule: String,
    val refillDate: String,
    val prescriber: String,
    val pharmacy: String,
    val status: String
)

@Entity(tableName = "appointments")
data class AppointmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val doctorName: String,
    val specialty: String,
    val date: String,
    val time: String,
    val location: String,
    val symptoms: String,
    val questions: String,
    val followUpNotes: String,
    val completed: Boolean
)
