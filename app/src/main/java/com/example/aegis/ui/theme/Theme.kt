package com.example.aegis.data

import com.example.aegis.model.Appointment
import com.example.aegis.model.EmergencyProfile
import com.example.aegis.model.Medication

object SampleData {
    fun defaultProfile(): EmergencyProfile = EmergencyProfile()

    fun medications(): List<Medication> = listOf(
        Medication(
            name = "Lisinopril",
            dosage = "10 mg",
            schedule = "Daily • 8:00 AM",
            refillDate = "2026-12-10",
            prescriber = "Dr. A. Chen",
            pharmacy = "Northside Pharmacy",
            status = "On track"
        ),
        Medication(
            name = "Atorvastatin",
            dosage = "20 mg",
            schedule = "Daily • 9:00 PM",
            refillDate = "2026-11-22",
            prescriber = "Dr. A. Chen",
            pharmacy = "Northside Pharmacy",
            status = "Refill soon"
        ),
        Medication(
            name = "Vitamin D3",
            dosage = "2000 IU",
            schedule = "Weekly • Saturday",
            refillDate = "2026-10-12",
            prescriber = "Self care",
            pharmacy = "Health Mart",
            status = "On track"
        )
    )

    fun appointments(): List<Appointment> = listOf(
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
    )
}
