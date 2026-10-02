package com.example.aegis.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aegis.TitleRow
import com.example.aegis.model.EmergencyProfile

@Composable
fun AssistantScreen(
    profile: EmergencyProfile,
    onNavigateToEmergency: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TitleRow("Health AI Assistant")
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("How can I help today?", MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Ask about medication timing, symptoms, your care plan, or emergency info.")
                Spacer(modifier = Modifier.height(12.dp))
                val suggestions = listOf(
                    "What are my current medications?",
                    "Do I need a refill soon?",
                    "Summarize my medical history",
                    "What should I bring to my next visit?"
                )
                suggestions.forEach {
                    Text("• $it", modifier = Modifier.padding(top = 4.dp))
                }
            }
        }

        Button(
            onClick = onNavigateToEmergency,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626))
        ) {
            Text("Open Emergency Profile")
        }
    }
}
