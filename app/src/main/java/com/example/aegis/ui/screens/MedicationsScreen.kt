package com.example.aegis.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aegis.TitleRow
import com.example.aegis.model.EmergencyProfile
import com.example.aegis.model.MedicalMemoryEntry

@Composable
fun HealthScreen(
    profile: EmergencyProfile,
    onNavigateToAssistantWithQuery: (String) -> Unit
) {
    val records = listOf(
        MedicalMemoryEntry("Hypertension", "Diagnosis", "2023-09-14", "Long-term blood pressure management and checkups."),
        MedicalMemoryEntry("Lisinopril 10 mg", "Medication", "2023-10-01", "Daily dose; continue through refills."),
        MedicalMemoryEntry("Lipid Panel", "Test", "2024-02-18", "Cholesterol improved with statin therapy."),
        MedicalMemoryEntry("Annual Physical", "Hospital / Visit", "2024-04-19", "Reviewed symptoms and medication adherence."),
        MedicalMemoryEntry("Peanuts", "Allergy", "2018-10-02", "Avoid peanut-based foods and check labels."),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TitleRow("Medical Memory")
        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Care Summary", color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Conditions: ${profile.conditions.joinToString()}", color = Color(0xFFCCF7FF))
                Text("Allergies: ${profile.allergies.joinToString()}", color = Color(0xFFCCF7FF))
                Text("Physician: ${profile.physician}", color = Color(0xFFCCF7FF))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(records) { item ->
                HealthMemoryCard(item)
            }
        }
    }
}

@Composable
fun HealthMemoryCard(item: MedicalMemoryEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(item.category, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                Text(item.date, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(item.title)
            Spacer(modifier = Modifier.height(4.dp))
            Text(item.detail, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
