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
import com.example.aegis.TitleRow
import com.example.aegis.model.Appointment

@Composable
fun AppointmentsScreen(appointments: List<Appointment>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TitleRow("Doctor Visits")
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(appointments) { item ->
                AppointmentCard(item)
            }
        }
    }
}

@Composable
fun AppointmentCard(item: Appointment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(item.doctorName)
                if (item.completed) {
                    Text("Completed", color = Color(0xFF22C55E))
                } else {
                    Text("Upcoming", color = Color(0xFF67E8F9))
                }
            }

            Text("${item.specialty} • ${item.date} • ${item.time}", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Location: ${item.location}")

            Spacer(modifier = Modifier.height(8.dp))
            Text("Symptoms:")
            item.symptoms.forEach {
                Text("• $it")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("Questions:")
            item.questions.forEach {
                Text("• $it")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("Follow-up:")
            Text(item.followUpNotes)
        }
    }
}
