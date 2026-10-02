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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aegis.TitleRow
import com.example.aegis.model.Medication

@Composable
fun MedicationsScreen(
    medications: List<Medication>,
    onAddMedication: () -> Unit = {},
    onEditMedication: (Medication) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TitleRow("Medication Center")
        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onAddMedication, modifier = Modifier.fillMaxWidth()) {
            Text("Add Medication")
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(medications) { med ->
                MedicationCard(med, onEditMedication)
            }
        }
    }
}

@Composable
fun MedicationCard(med: Medication, onEditMedication: (Medication) -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(med.name, fontWeight = FontWeight.Bold)
                    Text("${med.dosage} • ${med.schedule}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text(med.status, color = Color(0xFF22C55E), fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider()
            Spacer(modifier = Modifier.height(12.dp))

            Text("Prescriber: ${med.prescriber}")
            Text("Pharmacy: ${med.pharmacy}")
            Text("Refill due: ${med.refillDate}")

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onEditMedication(med) }) {
                Text("Save/Update")
            }
        }
    }
}
