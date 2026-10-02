package com.example.aegis.model

data class EmergencyContact(
    val name: String,
    val relationship: String,
    val phone: String,
    val email: String? = null,
    val canReceiveAlerts: Boolean = true,
    val canViewEmergencyProfile: Boolean = true
)

data class Medication(
    val name: String,
    val dosage: String,
    val schedule: String,
    val refillDate: String,
    val prescriber: String,
    val pharmacy: String,
    val status: String
)

data class HealthRecord(
    val type: String,
    val title: String,
    val date: String,
    val provider: String,
    val summary: String
)

data class Appointment(
    val doctorName: String,
    val specialty: String,
    val date: String,
    val time: String,
    val location: String,
    val symptoms: List<String>,
    val questions: List<String>,
    val followUpNotes: String,
    val completed: Boolean
)

data class MedicalMemoryEntry(
    val title: String,
    val category: String,
    val date: String,
    val detail: String
)

data class EmergencyProfile(
    val name: String = "Maya Patel",
    val allergies: List<String> = listOf("Penicillin", "Peanuts"),
    val currentMedications: List<String> = listOf("Lisinopril 10 mg", "Atorvastatin 20 mg"),
    val conditions: List<String> = listOf("Hypertension", "High Cholesterol"),
    val implantedDevices: List<String> = listOf("None reported"),
    val emergencyContacts: List<EmergencyContact> = listOf(
        EmergencyContact("Sam Patel", "Spouse", "+1 (555) 011-2030", "sam@email.com", true, true),
        EmergencyContact("Dr. A. Chen", "Primary Physician", "+1 (555) 201-1190", "chen@clinic.com", true, false)
    ),
    val bloodType: String = "A+",
    val physician: String = "Dr. A. Chen",
    val directives: String = "Keep comfortable, call spouse first, avoid invasive measures unless required by law.",
    val qrCodeText: String = "Aegis Emergency Profile: Maya Patel | A+ | allergies: Penicillin, Peanuts"
)
