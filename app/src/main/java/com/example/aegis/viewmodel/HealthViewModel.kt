package com.example.aegis.data

import com.example.aegis.model.Appointment
import com.example.aegis.model.EmergencyContact
import com.example.aegis.model.EmergencyProfile
import com.example.aegis.model.HealthUiState
import com.example.aegis.model.Medication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class HealthRepository(
    private val healthDao: HealthDao,
    private val secureHealthPreferences: SecureHealthPreferences
) {
    val profileFlow: Flow<EmergencyProfile> = healthDao.observeProfile().map { entity ->
        entity?.toEmergencyProfile() ?: secureHealthPreferences.loadProfile() ?: EmergencyProfile()
    }

    val medicationsFlow: Flow<List<Medication>> = healthDao.observeMedications().map { list ->
        list.map { it.toMedication() }
    }

    val appointmentsFlow: Flow<List<Appointment>> = healthDao.observeAppointments().map { list ->
        list.map { it.toAppointment() }
    }

    suspend fun seedDefaultData() {
        val existingProfile = healthDao.observeProfile().first()
        if (existingProfile == null) {
            val secureProfile = secureHealthPreferences.loadProfile()
            val defaultProfile = secureProfile ?: EmergencyProfile()
            healthDao.insertProfile(
                ProfileEntity(
                    id = 0,
                    name = defaultProfile.name,
                    bloodType = defaultProfile.bloodType,
                    allergies = defaultProfile.allergies.joinToString(";"),
                    currentMedications = defaultProfile.currentMedications.joinToString(";"),
                    conditions = defaultProfile.conditions.joinToString(";"),
                    physician = defaultProfile.physician,
                    directives = defaultProfile.directives,
                    emergencyContacts = defaultProfile.emergencyContacts.joinToString("|") { contact ->
                        "${contact.name},${contact.relationship},${contact.phone},${contact.email ?: ""},${contact.canReceiveAlerts},${contact.canViewEmergencyProfile}"
                    },
                    implantedDevices = defaultProfile.implantedDevices.joinToString(";")
                )
            )
            secureHealthPreferences.saveProfile(defaultProfile)
        }

        val existingMedications = healthDao.observeMedications().first()
        if (existingMedications.isEmpty()) {
            listOf(
                Medication("Lisinopril", "10 mg", "Daily • 8:00 AM", "2026-12-10", "Dr. A. Chen", "Northside Pharmacy", "On track"),
                Medication("Atorvastatin", "20 mg", "Daily • 9:00 PM", "2026-11-22", "Dr. A. Chen", "Northside Pharmacy", "Refill soon"),
                Medication("Vitamin D3", "2000 IU", "Weekly • Saturday", "2026-10-12", "Self care", "Health Mart", "On track")
            ).forEach { med ->
                healthDao.insertMedication(
                    MedicationEntity(
                        name = med.name,
                        dosage = med.dosage,
                        schedule = med.schedule,
                        refillDate = med.refillDate,
                        prescriber = med.prescriber,
                        pharmacy = med.pharmacy,
                        status = med.status
                    )
                )
            }
        }

        val existingAppointments = healthDao.observeAppointments().first()
        if (existingAppointments.isEmpty()) {
            listOf(
                Appointment(
                    doctorName = "Dr. A. Chen",
                    specialty = "Primary Care",
                    date = "2026-10-07",
                    time = "10:30 AM",
                    location = "Northside Clinic",
                    symptoms = listOf("Mild dizziness", "Occasional fatigue"),
                    questions = listOf("Medication side effects?", "When should I recheck blood pressure?"),
                    followUpNotes = "Continue current medications and monitor symptoms; follow-up in 4 weeks.",
                    completed = false
                ),
                Appointment(
                    doctorName = "Dr. B. Evans",
                    specialty = "Cardiology",
                    date = "2026-09-14",
                    time = "2:00 PM",
                    location = "Heart Center",
                    symptoms = listOf("No chest pain", "Stable exertion tolerance"),
                    questions = listOf("Review blood pressure trend"),
                    followUpNotes = "Blood pressure stable; continue plan and annual review.",
                    completed = true
                )
            ).forEach { appointment ->
                healthDao.insertAppointment(
                    AppointmentEntity(
                        doctorName = appointment.doctorName,
                        specialty = appointment.specialty,
                        date = appointment.date,
                        time = appointment.time,
                        location = appointment.location,
                        symptoms = appointment.symptoms.joinToString(";"),
                        questions = appointment.questions.joinToString(";"),
                        followUpNotes = appointment.followUpNotes,
                        completed = appointment.completed
                    )
                )
            }
        }
    }

    suspend fun updateProfile(profile: EmergencyProfile) {
        secureHealthPreferences.saveProfile(profile)
        healthDao.insertProfile(
            ProfileEntity(
                id = 0,
                name = profile.name,
                bloodType = profile.bloodType,
                allergies = profile.allergies.joinToString(";"),
                currentMedications = profile.currentMedications.joinToString(";"),
                conditions = profile.conditions.joinToString(";"),
                physician = profile.physician,
                directives = profile.directives,
                emergencyContacts = profile.emergencyContacts.joinToString("|") { contact ->
                    "${contact.name},${contact.relationship},${contact.phone},${contact.email ?: ""},${contact.canReceiveAlerts},${contact.canViewEmergencyProfile}"
                },
                implantedDevices = profile.implantedDevices.joinToString(";")
            )
        )
    }

    suspend fun addMedication(medication: Medication) {
        healthDao.insertMedication(
            MedicationEntity(
                name = medication.name,
                dosage = medication.dosage,
                schedule = medication.schedule,
                refillDate = medication.refillDate,
                prescriber = medication.prescriber,
                pharmacy = medication.pharmacy,
                status = medication.status
            )
        )
    }

    suspend fun addAppointment(appointment: Appointment) {
        healthDao.insertAppointment(
            AppointmentEntity(
                doctorName = appointment.doctorName,
                specialty = appointment.specialty,
                date = appointment.date,
                time = appointment.time,
                location = appointment.location,
                symptoms = appointment.symptoms.joinToString(";"),
                questions = appointment.questions.joinToString(";"),
                followUpNotes = appointment.followUpNotes,
                completed = appointment.completed
            )
        )
    }

    fun observeHealthState(): Flow<HealthUiState> = combine(
        profileFlow,
        medicationsFlow,
        appointmentsFlow
    ) { profile, meds, appts ->
        HealthUiState(
            profile = profile,
            medications = meds,
            appointments = appts
        )
    }
}

private fun ProfileEntity.toEmergencyProfile(): EmergencyProfile = EmergencyProfile(
    name = name,
    allergies = allergies.split(";").filter { it.isNotBlank() },
    currentMedications = currentMedications.split(";").filter { it.isNotBlank() },
    conditions = conditions.split(";").filter { it.isNotBlank() },
    implantedDevices = implantedDevices.split(";").filter { it.isNotBlank() },
    emergencyContacts = emergencyContacts.split("|").filter { it.isNotBlank() }.map { raw ->
        val parts = raw.split(",")
        if (parts.size >= 6) {
            EmergencyContact(
                name = parts[0],
                relationship = parts[1],
                phone = parts[2],
                email = parts[3].ifBlank { null },
                canReceiveAlerts = parts[4].toBooleanStrictOrNull() ?: true,
                canViewEmergencyProfile = parts[5].toBooleanStrictOrNull() ?: true
            )
        } else {
            EmergencyContact("Unknown", "Contact", "", null)
        }
    },
    bloodType = bloodType,
    physician = physician,
    directives = directives
)

private fun MedicationEntity.toMedication(): Medication = Medication(
    name = name,
    dosage = dosage,
    schedule = schedule,
    refillDate = refillDate,
    prescriber = prescriber,
    pharmacy = pharmacy,
    status = status
)

private fun AppointmentEntity.toAppointment(): Appointment = Appointment(
    doctorName = doctorName,
    specialty = specialty,
    date = date,
    time = time,
    location = location,
    symptoms = symptoms.split(";").filter { it.isNotBlank() },
    questions = questions.split(";").filter { it.isNotBlank() },
    followUpNotes = followUpNotes,
    completed = completed
)
