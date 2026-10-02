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
import com.example.aegis.model.Medication

@Composable
fun EditMedicationScreen(
    onSave: (Medication) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var schedule by remember { mutableStateOf("") }
    var refillDate by remember { mutableStateOf("") }
    var prescriber by remember { mutableStateOf("") }
    var pharmacy by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("On track") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Add Medication")

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Medication") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = dosage, onValueChange = { dosage = it }, label = { Text("Dosage") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = schedule, onValueChange = { schedule = it }, label = { Text("Schedule") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = refillDate, onValueChange = { refillDate = it }, label = { Text("Refill Date") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = prescriber, onValueChange = { prescriber = it }, label = { Text("Prescriber") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = pharmacy, onValueChange = { pharmacy = it }, label = { Text("Pharmacy") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = status, onValueChange = { status = it }, label = { Text("Status") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onCancel) { Text("Cancel") }
            Button(onClick = {
                if (name.isNotBlank()) {
                    onSave(
                        Medication(
                            name = name,
                            dosage = dosage,
                            schedule = schedule,
                            refillDate = refillDate,
                            prescriber = prescriber,
                            pharmacy = pharmacy,
                            status = status
                        )
                    )
                }
            }) {
                Text("Save")
            }
        }
    }
}
