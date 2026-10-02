package com.example.aegis.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aegis.TitleRow
import com.example.aegis.model.EmergencyContact
import com.example.aegis.model.EmergencyProfile

@Composable
fun ProfileSecurityScreen(
    profile: EmergencyProfile,
    onOpenOnboarding: () -> Unit = {},
    onEditProfile: () -> Unit = {},
    onAddContact: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TitleRow("Security & Caregiver Access")

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Emergency Contacts", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                profile.emergencyContacts.forEach { contact ->
                    ContactRow(contact)
                }
            }
        }

        Button(onClick = onEditProfile, modifier = Modifier.fillMaxWidth()) {
            Text("Edit Emergency Profile")
        }

        OutlinedButton(
            onClick = onOpenOnboarding,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Complete Safety Setup")
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Advance Directives", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(profile.directives)
            }
        }
    }
}

@Composable
fun ContactRow(contact: EmergencyContact) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(contact.name, fontWeight = FontWeight.SemiBold)
            Text("${contact.relationship} • ${contact.phone}")
            if (contact.email != null) {
                Text(contact.email, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Icon(
            imageVector = if (contact.canReceiveAlerts) Icons.Default.Alarm else Icons.Default.CheckCircle,
            contentDescription = null,
            tint = if (contact.canReceiveAlerts) Color(0xFF67E8F9) else Color(0xFF22C55E)
        )
    }
}
