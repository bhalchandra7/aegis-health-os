package com.example.aegis.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aegis.model.EmergencyProfile

@Composable
fun EmergencyScreen(
    profile: EmergencyProfile,
    onCloseEmergency: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F0A0A))
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("EMERGENCY MODE", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 26.sp)
                Button(
                    onClick = onCloseEmergency,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B5563))
                ) {
                    Text("Close")
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2D1B1B)),
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Patient: ${profile.name}", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("Blood Type: ${profile.bloodType}", color = Color(0xFFFDECEC))
                    Text("Allergies: ${profile.allergies.joinToString()}", color = Color(0xFFFDECEC))
                    Text("Current Medications: ${profile.currentMedications.joinToString()}", color = Color(0xFFFDECEC))
                    Text("Medical Conditions: ${profile.conditions.joinToString()}", color = Color(0xFFFDECEC))
                    Text("Emergency Contacts: ${profile.emergencyContacts.joinToString { it.name }}", color = Color(0xFFFDECEC))
                    Text("Physician: ${profile.physician}", color = Color(0xFFFDECEC))
                    Text("Directives: ${profile.directives}", color = Color(0xFFFDECEC))
                }
            }

            Button(
                onClick = { /* Send alerts / call emergency contact */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Call Emergency Contacts", fontWeight = FontWeight.Bold)
            }
        }
    }
}
