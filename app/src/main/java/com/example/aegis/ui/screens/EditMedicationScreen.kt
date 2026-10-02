package com.example.aegis.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aegis.model.EmergencyProfile

@Composable
fun EditProfileScreen(
    profile: EmergencyProfile,
    onSave: (EmergencyProfile) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(profile.name) }
    var bloodType by remember { mutableStateOf(profile.bloodType) }
    var allergies by remember { mutableStateOf(profile.allergies.joinToString(", ")) }
    var medications by remember { mutableStateOf(profile.currentMedications.joinToString(", ")) }
    var conditions by remember { mutableStateOf(profile.conditions.joinToString(", ")) }
    var physician by remember { mutableStateOf(profile.physician) }
    var directives by remember { mutableStateOf(profile.directives) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Edit Emergency Profile")

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = bloodType, onValueChange = { bloodType = it }, label = { Text("Blood Type") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = allergies, onValueChange = { allergies = it }, label = { Text("Allergies") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = medications, onValueChange = { medications = it }, label = { Text("Current Medications") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = conditions, onValueChange = { conditions = it }, label = { Text("Medical Conditions") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = physician, onValueChange = { physician = it }, label = { Text("Physician") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = directives, onValueChange = { directives = it }, label = { Text("Advance Directives") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onCancel) { Text("Cancel") }
            Button(onClick = {
                onSave(
                    profile.copy(
                        name = name,
                        bloodType = bloodType,
                        allergies = allergies.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                        currentMedications = medications.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                        conditions = conditions.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                        physician = physician,
                        directives = directives
                    )
                )
            }) {
                Text("Save")
            }
        }
    }
}
