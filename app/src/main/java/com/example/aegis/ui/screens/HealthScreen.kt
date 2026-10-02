package com.example.aegis.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aegis.InfoPanelCard
import com.example.aegis.QuickMetricCard
import com.example.aegis.TitleRow
import com.example.aegis.model.EmergencyProfile

@Composable
fun HomeScreen(
    emergencyProfile: EmergencyProfile,
    onNavigateToEmergency: () -> Unit,
    onNavigateToMedications: () -> Unit,
    onNavigateToAppointments: () -> Unit,
    onNavigateToAssistant: () -> Unit,
    onNavigateToHealth: () -> Unit
) {
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleRow("Today's Overview")

        EmergencyQuickCard(
            profile = emergencyProfile,
            onClick = onNavigateToEmergency
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickMetricCard("BMI", "23.2", "Healthy")
            QuickMetricCard("BP", "118/76", "Stable")
            QuickMetricCard("Steps", "8,420", "Goal 10K")
        }

        InfoPanelCard(
            title = "Medical Memory",
            description = "Diagnoses, medications, procedures, allergies, visits, and recent health changes.",
            onClick = onNavigateToHealth
        )

        InfoPanelCard(
            title = "Medication Center",
            description = "Refill schedule, prescriptions, medication adherence, and med history.",
            onClick = onNavigateToMedications
        )

        InfoPanelCard(
            title = "Doctor Visit Assistant",
            description = "Prepare before appointments and review follow-up instructions after care.",
            onClick = onNavigateToAppointments
        )

        InfoPanelCard(
            title = "Health AI",
            description = "Ask questions about symptoms, medication timing, or care instructions.",
            onClick = onNavigateToAssistant
        )
    }
}

@Composable
fun EmergencyQuickCard(profile: EmergencyProfile, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF7F1D1D)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Emergency Profile", color = Color.White, fontWeight = FontWeight.Bold)
                Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${profile.name} • ${profile.bloodType}",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Allergies: ${profile.allergies.joinToString()}",
                color = Color(0xFFFDECEC)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Medications: ${profile.currentMedications.joinToString()}",
                color = Color(0xFFFDECEC),
                maxLines = 2
            )
        }
    }
}
